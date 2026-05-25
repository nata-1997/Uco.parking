# Infisical + UcoParking (secretos y cómo usarlos)

Este documento describe **qué secretos** usar en Infisical, **cómo subirlos** y **cómo arrancar** backend, Kong y el front leyendo esas variables desde la nube (sin guardar valores reales en Git).

> **Importante:** no subas a Git archivos con contraseñas reales. En el repo solo hay plantillas con **nombres de clave** (sin valores). **Infisical no es Azure Key Vault**: en Azure producción puedes seguir usando Key Vault (`application-prod.yml`); en local/dev usamos Infisical u otro inyector de env.

## 0. Enlazar el repositorio con Infisical

1. Instala la [CLI de Infisical](https://infisical.com/docs/cli/overview) y ejecuta `infisical login`.
2. En la raíz de **Uco.parking**: `infisical init` y elige el proyecto + entorno `dev`.
3. Opcional: copia [`.infisical.json.example`](../.infisical.json.example) a `.infisical.json` y rellena `workspaceId` (en la UI de Infisical suele llamarse **Project ID**). Puedes mapear ramas Git a entornos con `gitBranchToEnvironmentMapping`.

Scripts de ayuda (Windows): `scripts/run-spring-dev.ps1`, `scripts/run-sqlserver.ps1`, `scripts/run-kong.ps1`.

## 1. Nombres de secretos (entorno `dev`)

Usa **exactamente** estos nombres de clave en Infisical (pestaña **Secrets** del entorno **dev**) para que coincidan con Spring Boot, Vite y Docker Compose.

### Frontend (Vue / Vite) — carpeta del SPA

| Nombre | Valor de ejemplo (placeholder) |
|--------|----------------------------------|
| `VITE_AUTH0_DOMAIN` | `tu_dominio.auth0.com` |
| `VITE_AUTH0_CLIENT_ID` | `tu_client_id_aquí` |
| `VITE_AUTH0_AUDIENCE` | `https://uco-parking-api` |
| `VITE_DEV_API_PROXY_TARGET` | `http://127.0.0.1:8000` (Kong) o `http://127.0.0.1:8080` (Spring directo) |
| `VITE_API_BASE_URL` | Solo si llamas al API con URL absoluta (ver `src/config/api.js` del front) |

En desarrollo, define **`VITE_DEV_API_PROXY_TARGET`** en Infisical para que el proxy de Vite apunte a Kong (`8000`) o a Spring (`8080`).

### Backend (Spring Boot)

| Nombre | Notas |
|--------|--------|
| `SPRING_DATASOURCE_URL` | **JDBC completo** (debe empezar por `jdbc:sqlserver://`). Ejemplo: `jdbc:sqlserver://localhost:1433;databaseName=UCOParking;encrypt=true;trustServerCertificate=true`. **No** uses solo `localhost:1433` (Hikari/SQL Server fallan con *claims to not accept jdbcUrl*). Si prefieres host/puerto sueltos, borra esta clave y usa `DB_HOST` / `DB_PORT` / `DB_NAME` (ver `application.yml`). |
| `SPRING_DATASOURCE_USERNAME` | Usuario SQL Server. |
| `SPRING_DATASOURCE_PASSWORD` | Contraseña (solo en Infisical, nunca en Git). |
| `AUTH0_DOMAIN` | Dominio Auth0 **sin** `https://` (p. ej. `dev-xxx.us.auth0.com`). El API construye el issuer `https://<dominio>/`. |
| `AUTH0_ISSUER_URI` | **Opcional** si ya usas `AUTH0_DOMAIN`. Si defines ambos, gana `AUTH0_ISSUER_URI`. Ejemplo: `https://dev-xxx.us.auth0.com/` |
| `AUTH0_AUDIENCE` | Mismo identificador de API que en el front (`VITE_AUTH0_AUDIENCE`). |
| `AUTH0_CLIENT_ID` | Reservado para integraciones OAuth cliente (el API actual valida JWT con issuer/audience; puedes guardar el client del back en Infisical para despliegues futuros o scripts). |
| `AUTH0_CLIENT_SECRET` | Igual; **no** lo imprimas en logs. |

### Kong / Docker Compose

| Nombre | Uso |
|--------|-----|
| `KONG_PG_PASSWORD` | Contraseña del usuario PostgreSQL de Kong (**obligatoria** para `docker compose -f docker-compose-kong.yml`; sin ella Compose falla al interpolar). |
| `KONG_DB_NAME` | Nombre de la base PostgreSQL (por defecto `kong`). Se mapea a `KONG_PG_DATABASE` / `POSTGRES_DB`. |
| `KONG_PG_USER` | Opcional; por defecto `kong`. |

### Base de datos (SQL Server en Docker, perfil `sql`)

Si usas el servicio `sqlserver` del `docker-compose-kong.yml`:

| Nombre | Uso |
|--------|-----|
| `MSSQL_SA_PASSWORD` | Contraseña del usuario `sa` dentro del contenedor. Debe ser **la misma** que uses en el JDBC (`SPRING_DATASOURCE_URL` / contraseña) si conectas al contenedor. |

Si SQL Server está **en el host** (Windows), suele bastar con `SPRING_DATASOURCE_*` apuntando a `localhost` y el usuario/contraseña que ya tengas.

---

## 2. Cómo subir los secretos a Infisical

### Opción A — Manual (entorno **dev**)

1. Entra en [Infisical](https://infisical.com) y abre tu **proyecto**.
2. Selecciona el entorno **`dev`**.
3. Pulsa **+ Add Secret** y crea cada fila: **Name** = nombre de la tabla anterior, **Value** = tu valor real.
4. Para secretos compartidos entre back y front (p. ej. audience), usa **el mismo texto** en `AUTH0_AUDIENCE` y `VITE_AUTH0_AUDIENCE`.

### Opción B — Importar desde archivo `.env`

1. Usa `docs/infisical-import-dev.env.template` como lista de claves; exporta desde Infisical o rellena valores **solo** en un archivo local ignorado (`infisical-dev.env` está en `.gitignore`).
2. En Infisical: **Import** (*Upload .env*) si subes un `.env` generado localmente (no lo subas a Git).

### Opción C — CLI de Infisical

1. Instala la [CLI de Infisical](https://infisical.com/docs/cli/overview).
2. Autenticación: `infisical login` (o el método que use tu organización).
3. En la raíz del proyecto (donde tengas `.infisical.json` tras `infisical init`), puedes cargar secretos desde un `.env` local:

```bash
infisical secrets set --env=dev --file=./infisical-dev.env
```

(Ajusta la ruta al archivo; no subas ese archivo a Git.)

Consulta la documentación oficial de Infisical para **sincronización**, **rotación** y **accesos por equipo**.

---

## 3. Cómo arrancar la app leyendo secretos desde Infisical

La idea es que Infisical **inyecte variables de entorno** en el proceso que ejecuta Maven, Docker o npm; la aplicación **no** llama a la API de Infisical en runtime (salvo que más adelante integres un SDK): solo lee `System.getenv` / el entorno del proceso.

### Backend (Spring Boot)

Desde la raíz de **Uco.parking**:

```bash
infisical run --env=dev -- mvn spring-boot:run
```

O en tu IDE: define las mismas variables en la **Run Configuration** exportando antes con `infisical run` o usando el plugin/mecanismo que use tu equipo.

Spring Boot reconoce de forma estándar `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME` y `SPRING_DATASOURCE_PASSWORD`. El issuer de Auth0 se resuelve con `AUTH0_ISSUER_URI` o, si falta, con `AUTH0_DOMAIN` (ver `Auth0RuntimeSettings`).

### Kong + PostgreSQL (`docker-compose-kong.yml`)

Con las variables ya en el entorno del shell (p. ej. tras `infisical run`):

```bash
infisical run --env=dev -- docker compose -f docker-compose-kong.yml up -d
```

Añade `--profile sql` solo si quieres el contenedor **SQL Server** definido dentro de ese mismo compose (y define `MSSQL_SA_PASSWORD` en Infisical). El SQL de desarrollo habitual es el de `docker compose.yml` en la raíz del back.

### Frontend (Vue)

En la carpeta del repositorio del SPA (p. ej. `UcoParking-frontend`):

```bash
infisical run --env=dev -- npm run dev
```

En la rama **`BaúlSecretos`** del front puedes usar `npm run dev:secrets` (mismo comando). Vite solo expone `VITE_*`; `infisical run` debe ejecutarse **antes** de `npm run dev` / `npm run build`.

---

## 4. Archivos útiles en el repo

| Archivo | Propósito |
|---------|-----------|
| [docs/infisical-import-dev.env.template](infisical-import-dev.env.template) | Lista de **nombres** de claves (valores vacíos; rellena solo en Infisical). |
| [docs/INFISICAL_RAMA_BAULSECRETOS.md](INFISICAL_RAMA_BAULSECRETOS.md) | Flujo de la rama de pruebas back + front. |
| [docker-compose-kong.yml](../docker-compose-kong.yml) | Lee `KONG_*` y `MSSQL_SA_PASSWORD` del entorno. |

Si necesitas un solo checklist imprimible, usa la plantilla `.env` y marca en Infisical cada clave como creada.
