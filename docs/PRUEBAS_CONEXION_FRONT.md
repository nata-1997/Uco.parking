# Probar back (Uco.parking) + front (UcoParking-frontend)

## Atajo: SQL + base + API (Windows)

Desde la raíz del back, con Infisical (`dev`):

```bat
infisical run --env=dev -- scripts\dev-setup-and-run-api.cmd
```

(Levanta SQL en Docker, crea `UCOParking` si no existe y arranca Spring.) Más detalle: [DEV_API_SQL_Y_DATOS.md](DEV_API_SQL_Y_DATOS.md).

**Alternativa sin el atajo:** copia `.env.example` a `.env` y define al menos `MSSQL_SA_PASSWORD` (solo en tu máquina; no subas `.env`). **O** exporta desde **Infisical** a `.env`. Opcional: `application-secrets.properties.example` → `application-secrets.properties` en la raíz del back para `spring.datasource.password` si no llevas la contraseña en el `.env`.

## 1. SQL Server en Docker (raíz del back)

1. Secretos en **Infisical** (`MSSQL_SA_PASSWORD`, JDBC en `SPRING_DATASOURCE_*` o `DB_*`).
2. En la raíz del back:

   ```bash
   infisical run --env=dev -- docker compose up -d
   ```

   (Windows: `.\scripts\run-sqlserver.ps1`.)

3. Crea la base **una vez**:
   - **Opción A (CMD + Infisical):** con el contenedor en marcha y `MSSQL_SA_PASSWORD` en el entorno:  
     `infisical run --env=dev -- scripts\create-ucoparking-database.cmd`  
     (En Windows, Infisical no ejecuta `.ps1` directo; el `.cmd` llama a PowerShell.)
   - **Opción B:** SSMS / Azure Data Studio / `sqlcmd` contra `localhost,1433`:

```sql
CREATE DATABASE UCOParking;
```

   Si ves `Cannot open database "UCOParking" ... login failed`, suele ser **base no creada** o **contraseña distinta** entre `MSSQL_SA_PASSWORD` (Docker) y `SPRING_DATASOURCE_PASSWORD` / `DB_PASSWORD` (Infisical). Deben coincidir con el `sa` del mismo servidor. Si cambiaste la contraseña en Infisical **después** del primer `up`, el volumen de Docker puede seguir con la **SA antigua**: ver [TROUBLESHOOTING_SQL_DOCKER.md](TROUBLESHOOTING_SQL_DOCKER.md) y `infisical run --env=dev -- scripts\diagnose-docker-sql.cmd`. Si en Windows ya tienes otro SQL en **1433**, Docker puede no poder publicar el puerto: o detén el SQL local o cambia el mapeo de puertos en `docker-compose.yml`.

4. Arranca Spring con perfil `dev` vía Infisical (Hibernate `ddl-auto: update` en dev creará/actualizará tablas). Si existe `.env` en la raíz, la app lo carga al inicio (ver [SECRETOS_E_INFISICAL.md](SECRETOS_E_INFISICAL.md)).

## 2. Backend

- URL API: `http://127.0.0.1:8080/uco-parking/api/v1/...`
- **Opcional — Kong:** `infisical run --env=dev -- docker compose -f docker-compose-kong.yml up -d` → `http://127.0.0.1:8000/uco-parking/api/v1/...` ([KONG_UCOPARKING.md](KONG_UCOPARKING.md)). Requiere **`KONG_PG_PASSWORD`** en Infisical.
- **Opcional — WAF (ModSecurity + CRS) delante de Kong:** el mismo compose con `--profile waf` → API vía `http://127.0.0.1:9080/uco-parking/...` ([MODSECURITY_UCOPARKING.md](MODSECURITY_UCOPARKING.md)); en Infisical usa `VITE_DEV_API_PROXY_TARGET=http://127.0.0.1:9080` cuando el WAF esté levantado.
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
