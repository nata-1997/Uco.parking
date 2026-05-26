# Uco.parking
Proyecto Uco Software II

## Desarrollo local con el front (`UcoParking-frontend`)

1. **Secretos:** copia `.env.example` → `.env` en la raíz de este repo (o exporta desde **Infisical** a `.env`). La API **carga `.env` al arrancar** si existe. Opcional: `application-secrets.properties.example` → `application-secrets.properties` (misma carpeta raíz) para contraseña de BD u otras claves. Detalle y tabla de variables: [docs/SECRETOS_E_INFISICAL.md](docs/SECRETOS_E_INFISICAL.md).
2. **Base de datos (Docker):** define al menos `MSSQL_SA_PASSWORD` en `.env`. `docker compose up -d` y crea la base `UCOParking` una vez (ver [docs/PRUEBAS_CONEXION_FRONT.md](docs/PRUEBAS_CONEXION_FRONT.md)).
3. Arranca Spring (perfil `dev` por defecto): el API queda en `http://127.0.0.1:8080/uco-parking/api/v1`.
4. **Opcional:** Kong en `docker compose -f docker-compose-kong.yml up -d` → mismo API vía `http://127.0.0.1:8000/uco-parking/...` ([docs/KONG_UCOPARKING.md](docs/KONG_UCOPARKING.md)).
5. En `D:\UcoParking-frontend\UcoParking`: `.env.example` → `.env.local` con **tu** Auth0 y, si hace falta, `VITE_DEV_API_PROXY_TARGET=http://127.0.0.1:8080` (o `8000` si usas Kong); luego `npm install` y `npm run dev` (puerto 5173).

Pasos detallados y notas para tu compañera: [docs/PRUEBAS_CONEXION_FRONT.md](docs/PRUEBAS_CONEXION_FRONT.md). Kong: [docs/KONG_UCOPARKING.md](docs/KONG_UCOPARKING.md).
