# Secretos locales, Infisical y pruebas (API + SendGrid)

## Resumen

1. **Infisical** guarda los secretos del equipo (entorno *Desarrollo*, etc.).
2. **Exportas** a un archivo `.env` en la **raíz de este repo** (mismo nivel que `pom.xml`).
3. Al arrancar Spring Boot, la app **carga automáticamente** ese `.env` (si existe) y rellena variables como `SENDGRID_API_KEY` **sin** pisar lo que ya definiste en el sistema operativo o en la Run Configuration de IntelliJ.
4. Opcional: sigue usando `application-secrets.properties` en la raíz para contraseña de BD si no quieres ponerla en `.env`.

Los archivos `.env` y `application-secrets.properties` están en `.gitignore`.

---

## Nombres recomendados en Infisical (alineados con el API)

Usa **MAYÚSCULAS_CON_GUIONES_BAJO** en Infisical; al exportar a `.env` coinciden con lo que lee Spring.

### Base de datos (elige una estrategia)

| En Infisical | Uso |
|--------------|-----|
| `SPRING_DATASOURCE_URL` | JDBC completo (como en tu proyecto Infisical). Si está definido, tiene prioridad sobre la URL armada con `DB_*`. |
| `SPRING_DATASOURCE_USERNAME` | Usuario SQL Server. |
| `SPRING_DATASOURCE_PASSWORD` | Contraseña SQL Server. |

**Alternativa local (Docker):** si no exportas `SPRING_DATASOURCE_*`, puedes dejar solo `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USERNAME`, `DB_PASSWORD` en `.env` (ver `.env.example`) o usar `application-secrets.properties` con `spring.datasource.password`.

### Auth0 (API protegida con JWT)

| En Infisical | Uso |
|--------------|-----|
| `AUTH0_ISSUER_URI` | **Obligatorio para validar tokens:** `https://TU-TENANT.auth0.com/` (incluye `https://` y barra final si tu tenant lo exige). |
| `AUTH0_AUDIENCE` | Identificador del API en Auth0 (mismo que en el front). |

Si en Infisical solo tienes `AUTH0_DOMAIN` (host sin `https://`), añade también `AUTH0_ISSUER_URI` con el valor completo del issuer (Auth0 → Applications → API → *Issuer* o documentación de tu tenant).

### SendGrid (correo al reservar cupo)

| En Infisical | Uso |
|--------------|-----|
| `SENDGRID_API_KEY` | API Key de SendGrid (`SG....`). |
| `SENDGRID_FROM_EMAIL` | Remitente **verificado** en SendGrid. |
| `SENDGRID_FROM_NAME` | Opcional; por defecto el YAML usa `UcoParking`. |
| `SENDGRID_ENABLED` | Opcional; `true` / `false`. Con `false` o sin API key no se envía correo pero la reserva funciona. |

### Redis (Valkey en Docker)

| En Infisical | Uso |
|--------------|-----|
| `REDIS_HOST` | Por defecto `localhost` si no defines nada. |
| `REDIS_PORT` | Por defecto `6380` (puerto mapeado en `docker-compose.yml`). |

### Docker SQL Server (solo para `docker compose`)

| En `.env` (raíz) | Uso |
|------------------|-----|
| `MSSQL_SA_PASSWORD` | **Obligatorio** para levantar el contenedor (`docker-compose.yml`). Puede ir en el mismo `.env` que exportes desde Infisical si añades esa clave al proyecto Infisical o la fusionas a mano. |

### Kong / front (no los lee este JAR)

`KONG_*`, `UCOPARKING_UPSTREAM`, `VITE_*` son para **otros servicios** (Kong, frontend). Pueden vivir en el mismo `.env` del monorepo o en otro archivo; Spring los ignora si no los referencias en `application.yml`.

---

## Flujo de trabajo: crear → exportar → probar

### 1) En Infisical

- Crea o revisa los secretos del entorno **Desarrollo** con las claves de la tabla anterior.
- Asegúrate de tener **SendGrid** (`SENDGRID_*`) si quieres probar el correo al reservar.

### 2) Exportar a `.env`

- En la lista de secretos, usa el icono de **exportar / descargar** y elige formato **`.env`**.
- Guarda el archivo como **`.env`** en la raíz de **Uco.parking** (junto a `pom.xml`).

Si mezclas export de Infisical con variables solo locales (`MSSQL_SA_PASSWORD`), puedes **concatenar** al final del `.env` las líneas que falten o usar dos archivos y fusionar a mano una sola vez.

### 3) Arrancar la API

Desde la raíz del repo:

```bash
docker compose up -d
mvn spring-boot:run
```

La app carga `.env` al inicio; el context path sigue siendo `/uco-parking`.

### 4) Probar el correo

1. Estudiante registrado con **correo real** al que SendGrid pueda enviar.
2. `POST .../parking-spots/{spotCode}/reserve` con ese `studentId`.
3. Revisa logs: mensaje tipo *Correo de reserva enviado* o el warning de SendGrid con HTTP/cuerpo si falla (remitente no verificado, etc.).

---

## Opción: Infisical CLI (sin archivo `.env` manual)

Si tienes [Infisical CLI](https://infisical.com/docs/cli/overview) logueado al proyecto:

```bash
infisical run --env=dev -- mvn spring-boot:run
```

Inyecta variables en el proceso; en ese caso **no hace falta** `.env` en disco para esa sesión (sí puede seguir haciendo falta `MSSQL_SA_PASSWORD` en el entorno del CLI o en `.env` para Docker).

---

## Orden de prioridad (qué valor gana)

1. Variable de entorno del **sistema** o **Run Configuration** (IntelliJ).
2. Propiedad **`-D`** en la línea de comandos de Java.
3. Archivo **`.env`** en la raíz (lo que carga la app si no choca con lo anterior).
4. **`application-secrets.properties`** (importado en perfil `dev`) para propiedades `spring.*` / `sendgrid.*` según lo que definas ahí.

Si algo “no se aplica”, suele ser porque ya está definido en un nivel con más prioridad.
