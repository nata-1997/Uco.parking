# Kong (API Gateway) y UcoParking

Kong actúa como **proxy** delante del Spring Boot: el navegador o el proxy de Vite pueden apuntar al **puerto 8000** en lugar del **8080** directo.

## Arranque

1. **SQL Server y Spring** como siempre (ver [PRUEBAS_CONEXION_FRONT.md](PRUEBAS_CONEXION_FRONT.md)): base `UCOParking`, `docker compose up -d` para MSSQL, Spring en **8080** con `context-path` `/uco-parking`.
2. En la raíz del back:

   ```bash
   docker compose -f docker-compose-kong.yml up -d
   ```

3. El contenedor **`kong-bootstrap-ucoparking`** (una sola vez por `up`) registra en Kong:
   - **Servicio** `ucoparking-backend` → `http://host.docker.internal:8080` (tu JVM en el host).
   - **Ruta** con prefijo `/uco-parking` y `strip_path=false` (coincide con Tomcat).

## URLs

| Uso | URL |
|-----|-----|
| Sin Kong (directo) | `http://127.0.0.1:8080/uco-parking/api/v1/...` |
| Con Kong | `http://127.0.0.1:8000/uco-parking/api/v1/...` |
| Admin API Kong | `http://127.0.0.1:8001` (p. ej. `GET /services`) |

## Frontend (Vite)

En `.env.local` del front, apunta el proxy de desarrollo al gateway:

```env
VITE_DEV_API_PROXY_TARGET=http://127.0.0.1:8000
```

Las rutas del front siguen siendo relativas (`/uco-parking/...`); solo cambia el **origen** del proxy.

## Cambiar el upstream (Spring en otro host o puerto)

Antes de `docker compose -f docker-compose-kong.yml up`, define en tu entorno o en `.env` en la raíz del back (Compose lee variables para sustitución):

```env
UCOPARKING_UPSTREAM=http://host.docker.internal:9090
```

Luego vuelve a levantar el bootstrap (o borra el servicio en Kong y ejecuta `up` de nuevo). Para **solo** repetir el registro sin recrear Kong:

```bash
docker compose -f docker-compose-kong.yml run --rm kong-bootstrap-ucoparking
```

(Si el servicio ya existe con otra URL, actualízalo con la Admin API `PATCH /services/ucoparking-backend` o elimínalo y vuelve a ejecutar el bootstrap.)

## Linux

`extra_hosts: host.docker.internal:host-gateway` en el servicio **kong** permite que Kong resuelva el host donde corre Spring. Si algo falla, comprueba versión de Docker (20.10+).

## Script `bootstrap-ucoparking.sh`

Debe guardarse con finales de línea **LF** (Git lo fuerza con `.gitattributes`). Si en Windows el contenedor muestra `illegal option` en `set`, convierte el archivo a LF y vuelve a ejecutar `docker compose -f docker-compose-kong.yml up kong-bootstrap-ucoparking`.

## Auth0 y CORS

El **Origin** del navegador sigue siendo el del front (p. ej. `http://localhost:5173`); Kong reenvía la petición a Spring. La configuración CORS actual del back aplica igual.

## SQL Server dentro del mismo compose Kong

Opcional: `docker compose -f docker-compose-kong.yml --profile sql up -d` (perfil `sql`). En Windows el contenedor de SQL a veces falla por permisos de volumen; lo habitual es usar el `docker compose.yml` principal de MSSQL en **1433**.
