# Strapi en Docker (UcoParking)

Aquí tienes **PostgreSQL** + **Strapi en modo desarrollo** (`npm run develop`), alineado con la [documentación oficial de Strapi 5 — Docker](https://docs.strapi.io/cms/installation/docker).

Strapi no publica una imagen oficial única: se construye desde **tu carpeta del proyecto** (`cms/`).

## Requisitos

- Docker Desktop (o Docker Engine) + Docker Compose v2
- Node.js 20+ en tu máquina (solo para generar el proyecto la primera vez)

## 1. Variables de entorno

En la carpeta `docker/strapi/`:

```bash
copy env.example .env
```

Edita `.env` y define al menos `DATABASE_PASSWORD` y todos los secretos (`APP_KEYS`, `JWT_SECRET`, etc.). **No subas `.env` a Git** (ya está ignorado en la raíz del repo).

## 2. Levantar solo la base de datos

Desde `docker/strapi/`:

```bash
docker compose up -d strapiDB
```

Espera unos segundos a que el contenedor esté **healthy**. PostgreSQL queda accesible en el host como **`localhost:5433`** (usuario y base `strapi`, contraseña la de `DATABASE_PASSWORD` en tu `.env`).

## 3. Crear el proyecto Strapi (una sola vez)

En la misma carpeta `docker/strapi/`, con la base ya en marcha.

**Linux / macOS (Git Bash):**

```bash
npx create-strapi@latest cms --no-run --skip-cloud --use-npm \
  --dbclient=postgres \
  --dbhost=127.0.0.1 \
  --dbport=5433 \
  --dbname=strapi \
  --dbusername=strapi \
  --dbpassword=TU_DATABASE_PASSWORD_DEL_ARCHIVO_ENV
```

**Windows (CMD), una sola línea:**

```bat
npx create-strapi@latest cms --no-run --skip-cloud --use-npm --dbclient=postgres --dbhost=127.0.0.1 --dbport=5433 --dbname=strapi --dbusername=strapi --dbpassword=TU_DATABASE_PASSWORD_DEL_ARCHIVO_ENV
```

Sustituye `TU_DATABASE_PASSWORD_DEL_ARCHIVO_ENV` por el mismo valor que `DATABASE_PASSWORD` en tu `.env`.

```bash
cd cms
copy ..\dot-dockerignore.template .dockerignore
cd ..
```

## 4. Arrancar Strapi en Docker

```bash
docker compose up --build
```

- Admin: **http://localhost:1337/admin**
- API: **http://localhost:1337**

Para segundo plano: `docker compose up --build -d`.

Para parar: `docker compose down` (añade `-v` si quieres borrar también el volumen de Postgres).

## 5. Conectar el backend Spring

En el API (variables o `application.yml`):

- `STRAPI_URL=http://localhost:1337`
- `STRAPI_REMOTE_ENABLED=true` (por defecto)
- Token de Strapi si lo usas: `STRAPI_API_TOKEN=...`

Crea las colecciones y permisos como en **`docs/STRAPI.md`** e importa las semillas de `docs/strapi/`.

## Problemas frecuentes

| Síntoma | Qué revisar |
|--------|-------------|
| Error de **Sharp / libvips** en build Alpine | Prueba en `strapi.Dockerfile` la imagen base `node:22-slim` y dependencias `apt` según la doc de Strapi. |
| **Apple Silicon** y imagen de base de datos | En `strapiDB` puedes descomentar `platform: linux/amd64` si alguna imagen no tiene ARM. |
| El contenedor **Strapi** no arranca | Que exista `cms/package.json` y que el paso 3 se haya ejecutado contra el Postgres del paso 2 (misma contraseña). |
| Puerto **1337** ocupado | Cambia en `docker-compose.yml` la asignación `1337:1337` por otra (p. ej. `1338:1337`) y usa esa URL en `STRAPI_URL`. |

## Producción

Para despliegue real usa build multi-etapa y `npm run start`, base de datos sin publicar el puerto al host y secretos gestionados por tu orquestador; ver la misma página de la documentación de Strapi (**Production**).
