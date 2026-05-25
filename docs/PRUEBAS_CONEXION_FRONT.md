# Probar back (Uco.parking) + front (UcoParking-frontend)

## 1. SQL Server en Docker (raíz del back)

1. Secretos en **Infisical** (`MSSQL_SA_PASSWORD`, JDBC en `SPRING_DATASOURCE_*` o `DB_*`).
2. En la raíz del back:

   ```bash
   infisical run --env=dev -- docker compose up -d
   ```

   (Windows: `.\scripts\run-sqlserver.ps1`.)

3. Crea la base **una vez** (SSMS, Azure Data Studio o `sqlcmd` contra `localhost,1433`):

```sql
CREATE DATABASE UCOParking;
```

4. Arranca Spring con perfil `dev` vía Infisical (Hibernate `ddl-auto: update` en dev creará/actualizará tablas).

## 2. Backend

- URL API: `http://127.0.0.1:8080/uco-parking/api/v1/...`
- **Opcional — Kong:** `infisical run --env=dev -- docker compose -f docker-compose-kong.yml up -d` → `http://127.0.0.1:8000/uco-parking/api/v1/...` ([KONG_UCOPARKING.md](KONG_UCOPARKING.md)). Requiere **`KONG_PG_PASSWORD`** en Infisical.
- CORS ya permite `http://localhost:5173` y `http://127.0.0.1:5173`.

Variables típicas (definidas en Infisical, no en Git):

- `SPRING_DATASOURCE_URL` / `SPRING_DATASOURCE_USERNAME` / `SPRING_DATASOURCE_PASSWORD`, o
- `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USERNAME`, `DB_PASSWORD`

Para dejar **todos los cupos libres** en la base: ejecuta `docs/reset-parking-spots-libres.sql` en SSMS o Azure Data Studio contra `UCOParking`.

## 3. Frontend (`D:\UcoParking-frontend\UcoParking`)

1. `npm install`
2. En la rama **`BaúlSecretos`**: `npm run dev:secrets` (secretos `VITE_*` desde Infisical). Requiere `infisical init` en esa carpeta.
3. Auth0: claves `VITE_AUTH0_DOMAIN`, `VITE_AUTH0_CLIENT_ID`, `VITE_AUTH0_AUDIENCE` en Infisical (referencia de nombres en el `.env.example` del repo del front).
4. Tras iniciar sesión con Auth0, el front consulta `GET /api/v1/students/lookup?email=...`: si existe estudiante, guarda el **UUID** en sesión. Opcional en dev: `VITE_STUDENT_UUID` en Infisical.
5. Proxy: define `VITE_DEV_API_PROXY_TARGET` en Infisical (p. ej. `http://127.0.0.1:8000` con Kong o `8080` sin Kong). Alternativa sin proxy: `VITE_API_BASE_URL` (ver código en `src/config/api.js`).

## 4. Tu compañera

Que use el mismo **entorno Infisical** (`dev`), `infisical login` / `infisical init` en su máquina y **no** suba secretos al repositorio.
