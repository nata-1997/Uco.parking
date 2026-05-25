# Uco.parking
Proyecto Uco Software II

## Desarrollo local con el front (`UcoParking-frontend`)

1. **Base de datos (Docker):** en la raíz de este repo, copia `.env.example` → `.env` y define `MSSQL_SA_PASSWORD`. Copia `application-secrets.properties.example` → `application-secrets.properties` con la **misma** contraseña en `spring.datasource.password`. Esos archivos están en `.gitignore` (cada persona usa las suyas).
2. `docker compose up -d` y crea la base `UCOParking` una vez (ver [docs/PRUEBAS_CONEXION_FRONT.md](docs/PRUEBAS_CONEXION_FRONT.md)).
3. Arranca Spring (perfil `dev` por defecto): el API queda en `http://127.0.0.1:8080/uco-parking/api/v1`.
4. En `D:\UcoParking-frontend\UcoParking`: `.env.example` → `.env.local` con **tu** Auth0 y, si hace falta, `VITE_DEV_API_PROXY_TARGET=http://127.0.0.1:8080`; luego `npm install` y `npm run dev` (puerto 5173).

Pasos detallados y notas para tu compañera: [docs/PRUEBAS_CONEXION_FRONT.md](docs/PRUEBAS_CONEXION_FRONT.md).
