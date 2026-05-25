# Probar back (Uco.parking) + front (UcoParking-frontend)

## 1. SQL Server en Docker

En la raíz del back (`Uco.parking`):

1. Copia `.env.example` a `.env` y pon tu `MSSQL_SA_PASSWORD` (solo en tu máquina; no subas `.env`).
2. Copia `application-secrets.properties.example` a `application-secrets.properties` y usa la **misma** contraseña en `spring.datasource.password`.
3. `docker compose up -d`
4. Crea la base **una vez** (SSMS, Azure Data Studio o `sqlcmd` contra `localhost,1433`):

```sql
CREATE DATABASE UCOParking;
```

5. Arranca Spring con perfil `dev` (por defecto en `application.yml`). Hibernate `ddl-auto: update` creará/actualizará tablas.

## 2. Backend

- URL API: `http://127.0.0.1:8080/uco-parking/api/v1/...`
- **Opcional — Kong (API Gateway):** `docker compose -f docker-compose-kong.yml up -d` y usa `http://127.0.0.1:8000/uco-parking/api/v1/...` (detalle en [docs/KONG_UCOPARKING.md](KONG_UCOPARKING.md)).
- CORS ya permite `http://localhost:5173` y `http://127.0.0.1:5173`.

Variables útiles (alternativa al archivo secrets):

- `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USERNAME`, `DB_PASSWORD`

Para dejar **todos los cupos libres** en la base (placa, franja y dueño en blanco): ejecuta `docs/reset-parking-spots-libres.sql` en SSMS o Azure Data Studio contra `UCOParking`.

## 3. Frontend (`D:\UcoParking-frontend\UcoParking`)

1. `npm install`
2. Copia `.env.example` a **`.env.local`** (ya ignorado por Git).
3. Configura **Auth0** (`VITE_AUTH0_DOMAIN`, `VITE_AUTH0_CLIENT_ID`).
4. Tras iniciar sesión con Auth0, el front consulta `GET /api/v1/students/lookup?email=...` (mismo correo que Auth0): si existe estudiante, guarda el **UUID** en sesión y entra a reservas (`/dashboard`). Si no existe, redirige a **Registrar estudiante**. Opcional en dev: `VITE_STUDENT_UUID` en `.env.local` fuerza un UUID fijo si no hay sesión.
5. Si el API no está en `127.0.0.1:8080`, ajusta `VITE_DEV_API_PROXY_TARGET` en `.env.local` (con Kong suele ser `http://127.0.0.1:8000`).
6. `npm run dev` → suele ser `http://localhost:5173`

El proxy de Vite envía `/uco-parking` al back; no hace falta CORS extra para llamadas relativas.

## 4. Tu compañera

Que repita los pasos con **sus** archivos locales (`.env`, `application-secrets.properties`, `.env.local`) y sus credenciales Auth0 / Docker. Nada de eso debe subirse al repositorio.
