# Docker: SQL + Valkey + API + front

Este repo incluye **imágenes y Compose** para levantar el stack completo. El **front vive en otro repositorio**; Compose usa un contexto adicional de build (`additional_contexts`) para copiar su código.

## Guía rápida (perfil `app`)

Ejecuta en **este orden** desde la raíz del back (`Uco.parking`):

| Paso | Qué hacer |
|------|-----------|
| 1 | Tener el front clonado en disco (carpeta con `package.json`). |
| 2 | Crear o editar **`.env`** en la raíz del back (ver [tabla de variables en .env](#tabla-de-variables-en-env)). Mínimo: `MSSQL_SA_PASSWORD`, `FRONTEND_BUILD_CONTEXT`, `VITE_AUTH0_DOMAIN`, `VITE_AUTH0_CLIENT_ID` (y `VITE_AUTH0_AUDIENCE` si el front lo exige). |
| 3 | `docker compose --profile app up -d --build` (o `build` antes si prefieres). |
| 4 | Abrir **`http://localhost/`** (o `http://localhost:PUERTO/` si usas `FRONTEND_HOST_PORT`). Usa **HTTP**, no HTTPS, salvo que añadas TLS. |

Infisical (recomendado en equipos): mismas claves que en la tabla, inyectadas con  
`infisical run --env=dev -- docker compose --profile app up -d --build`.

---

## Tabla: variables en `.env`

Archivo **`.env`** junto a `docker-compose.yml` (no se commitea; puedes partir de [`.env.example`](../.env.example)).

| Variable | Obligatoria (stack `app`) | Uso |
|----------|---------------------------|-----|
| `MSSQL_SA_PASSWORD` | Sí | SQL Server + contraseña que recibe el contenedor del API como `DB_PASSWORD`. |
| `FRONTEND_BUILD_CONTEXT` | Sí (para build del front) | Ruta absoluta a la carpeta del **otro repo** donde está `package.json` (p. ej. `D:/…/UcoParking-frontend/UcoParking`). Barras `/` recomendadas en Windows. |
| `FRONTEND_APP_SUBPATH` | No | Solo si `FRONTEND_BUILD_CONTEXT` apunta al **repo padre** y `package.json` está en subcarpeta (p. ej. `UcoParking`). Por defecto `.`. |
| `VITE_AUTH0_DOMAIN` | Sí (front usable) | Se inyectan en el **build** de Vite; sin ellas el SPA muestra “Faltan VITE_AUTH0…”. Mismos valores que en Infisical / `.env.local` del front. |
| `VITE_AUTH0_CLIENT_ID` | Sí (front usable) | ↑ |
| `VITE_AUTH0_AUDIENCE` | Según el front | ↑ |
| `VITE_API_BASE_URL` | No | Opcional; con Nginx del stack muchas veces basta vacío y rutas relativas bajo `/uco-parking/`. |
| `AUTH0_DOMAIN`, `AUTH0_AUDIENCE`, `AUTH0_ISSUER_URI` | No (back) | Validación JWT del API en contenedor; alineadas con el tenant del front si usas Auth0. |
| `BACKEND_HOST_PORT`, `FRONTEND_HOST_PORT` | No | Por defecto `8080` y `80`. |
| `SPRING_PROFILES_ACTIVE`, `DB_NAME`, … | No | Ver `docker-compose.yml`. |

### Comparacion Docker vs npm run dev (Vite / Auth0)

| Dónde trabajas | Dónde defines Auth0 / Vite para el SPA |
|----------------|----------------------------------------|
| **Docker** (imagen `ucoparking-frontend`) | Variables **`VITE_*`** en **`.env` de `Uco.parking`** (o en el entorno al hacer `docker compose build`). Hay que **reconstruir** el servicio `frontend` si cambias valores. |
| **Front en local** (`npm run dev`) | **`.env.local`** o Infisical + `npm run dev:secrets` en el repo del front (rama / flujo que indique el propio front; ver [PRUEBAS_CONEXION_FRONT.md](PRUEBAS_CONEXION_FRONT.md)). |

No son intercambiables: cambiar solo Infisical del front **no** actualiza la imagen Docker hasta que vuelvas a hacer **build** con esas variables visibles para Compose.

---

## Requisitos

- **Docker Desktop** (o Docker Engine) con **Compose v2.17+** (necesario para `additional_contexts` del front).
- **Memoria** suficiente para SQL Server + builds Maven/Node (recomendado **≥ 8 GB** RAM asignados a Docker).

## 1. Solo SQL Server + Valkey (sin API en contenedor)

```bash
infisical run --env=dev -- docker compose up -d
```

Crea la base `UCOParking` una vez (scripts o SSMS) como siempre.

## 2. Stack completo: back + front en Docker

1. Clona el front (rama que permita `npm run build`).
2. Rellena **`.env`** en la raíz del back según la [tabla](#tabla-de-variables-en-env).
3. Arranca y construye:

```powershell
docker compose --profile app up -d --build
```

| Servicio | Puerto host por defecto | URL útil |
|----------|---------------------------|----------|
| SQL Server | 1433 | JDBC `localhost,1433` desde el host |
| Valkey | 6380 | Redis CLI al puerto publicado |
| **backend** | 8080 | `http://localhost:8080/uco-parking/api/v1/...` |
| **frontend** | 80 | `http://localhost/` (Nginx sirve el SPA y **proxifica** `/uco-parking/` al back) |

### Build solo del back

```bash
docker compose build backend
```

(No hace falta `FRONTEND_BUILD_CONTEXT` para interpolar; el compose trae un valor por defecto solo para eso.)

## 3. Imagen del API (manual)

```bash
docker build -f docker/backend/Dockerfile -t ucoparking-backend .
```

---

## Problemas frecuentes

### A) `FRONTEND_BUILD_CONTEXT` / `package.json` no encontrado

- **Contexto vacío o raíz del back:** comprueba la ruta interpolada (debe ser la carpeta del front con `package.json`, no la del back):

  ```powershell
  docker compose config | Select-String uco_front
  ```

  Usa **`.env`** con `FRONTEND_BUILD_CONTEXT=D:/ruta/.../UcoParking`.
- **Repo padre sin `package.json` en la raíz:** o bien apunta el contexto a la subcarpeta correcta, o usa `FRONTEND_APP_SUBPATH` (ver [.env.example](../.env.example)).
- **Compose desde la carpeta del front:** el `docker-compose.yml` está en el back; usa `cd` a `Uco.parking` o `-f D:\…\Uco.parking\docker-compose.yml`.
- **PowerShell:** no pegues `$env:…` y `docker` en la misma línea sin `;`. Mejor **`.env`** que comillas tipográficas o rutas rotas.
- **Compose antiguo:** sin `additional_contexts`, actualiza Docker Desktop.
- **pnpm / yarn:** el Dockerfile del front usa **npm**; hace falta `package-lock.json` o adaptar el Dockerfile.

### B) “Faltan VITE_AUTH0_DOMAIN o VITE_AUTH0_CLIENT_ID” en el navegador (imagen Docker)

Las `VITE_*` se fijan en **`npm run build`**. Añádelas al **`.env` del back** y reconstruye:

```powershell
docker compose --profile app build frontend --no-cache
docker compose --profile app up -d
```

Ver la [tabla](#tabla-de-variables-en-env) y la sección **Comparacion Docker vs npm run dev** más arriba.

### C) SQL Server no arranca o el healthcheck nunca pasa

- Contraseña débil para `sa`.
- Puerto **1433** ocupado en el host: cambia el mapeo en `docker-compose.yml`; entre contenedores sigue `DB_HOST=sqlserver`, `DB_PORT=1433`.

### D) El back no conecta a SQL o a Valkey

Usa los nombres de servicio **`sqlserver`** y **`valkey`**; no `localhost` dentro del contenedor del API para esos servicios.

### E) CORS / Auth0 / URLs

- Con Nginx del front, el origen suele ser `http://localhost` y el API por `/uco-parking/`.
- Auth0: **Allowed Callback / Web Origins** con la URL real (p. ej. `http://localhost`).
- **HTTPS:** este stack es dev/staging sin TLS salvo que lo añadas.

### F) `ERR_CONNECTION_REFUSED` al abrir el front

- URL **`http://`** (no `https://`) al puerto publicado (`80` o `FRONTEND_HOST_PORT`).
- `docker ps` y `docker logs ucoparking-frontend` por si el contenedor cayó.
- Puerto **80** ocupado (IIS, etc.): define `FRONTEND_HOST_PORT=8088` en `.env` y abre `http://localhost:8088/`.

### G) Contenedores “orphan” (Kong, WAF)

Aparecen si antes levantaste **otro** compose en el mismo proyecto. No afectan al perfil `app`; puedes usar `docker compose --profile app up -d --remove-orphans` o bajar el otro stack con su `docker compose down`.

### H) Secretos en Git

No commitees `.env` con contraseñas; usa Infisical o `.env` local solo en tu máquina.

### J) En el mapa / reservas: `Unexpected token '<', "<!DOCTYPE "... is not valid JSON`

El navegador pidió **JSON** al API y recibió **HTML** (casi siempre el `index.html` del SPA).

- **Causa habitual con Docker + Nginx:** la URL base del API **no lleva** el prefijo del contexto Spring **`/uco-parking`**, y la petición cae en `location /` → Nginx devuelve el SPA en lugar de proxificar al back.
- **Qué hacer:** en el **`.env` del back** (y **rebuild** del front), define la base del API acorde a **cómo abres la web**:
  - Si entras por **`http://localhost`** (puerto 80 del contenedor Nginx), usa por ejemplo  
    `VITE_API_BASE_URL=/uco-parking/api/v1`  
    o  
    `VITE_API_BASE_URL=http://localhost/uco-parking/api/v1`
  - Si usas **`FRONTEND_HOST_PORT=8088`**, incluye ese puerto: `http://localhost:8088/uco-parking/api/v1`
- **Diagnóstico:** F12 → **Red** → petición fallida → mira **URL** y la pestaña **Vista previa** (si es HTML de página Vue/React, es la causa).
- **CORS:** si el front en `http://localhost` (puerto 80) llamaba al API en `http://localhost:8080`, el back ya permite orígenes `http://localhost:[*]` (incluye el 80 sin escribir `:80`).

---

## Archivos relevantes

| Ruta | Uso |
|------|-----|
| [docker-compose.yml](../docker-compose.yml) | SQL, Valkey, `backend` y `frontend` (perfil `app`) |
| [docker/backend/Dockerfile](../docker/backend/Dockerfile) | Imagen Spring Boot |
| [docker/frontend/Dockerfile](../docker/frontend/Dockerfile) | Build Vite + Nginx |
| [docker/frontend/nginx/default.conf](../docker/frontend/nginx/default.conf) | SPA + proxy `/uco-parking/` → `backend:8080` |
| [.dockerignore](../.dockerignore) | Reduce contexto de build del back |
| [.env.example](../.env.example) | Plantilla comentada de variables para Compose |
