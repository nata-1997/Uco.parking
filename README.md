# Uco.parking
Proyecto Uco Software II

## Desarrollo con Infisical (rama `BaúlSecretos` y pruebas)

Los secretos viven en **Infisical** (entorno `dev`), no en archivos locales del repositorio. Guía: [docs/INFISICAL_UCOPARKING.md](docs/INFISICAL_UCOPARKING.md) y [docs/INFISICAL_RAMA_BAULSECRETOS.md](docs/INFISICAL_RAMA_BAULSECRETOS.md).

1. **CLI:** [Infisical CLI](https://infisical.com/docs/cli/overview) → `infisical login` y en la raíz del repo `infisical init` (o copia [.infisical.json.example](.infisical.json.example) a `.infisical.json` y completa `workspaceId`).
2. **SQL Server (Docker, raíz del repo):** `.\scripts\run-sqlserver.ps1` o `infisical run --env=dev -- docker compose up -d` → crea la base `UCOParking` una vez (ver [docs/PRUEBAS_CONEXION_FRONT.md](docs/PRUEBAS_CONEXION_FRONT.md)).
3. **Spring (perfil `dev`):** `.\scripts\run-spring-dev.ps1` o `infisical run --env=dev -- .\mvnw.cmd spring-boot:run` → API en `http://127.0.0.1:8080/uco-parking/api/v1`.
4. **Kong (opcional):** `.\scripts\run-kong.ps1` o `infisical run --env=dev -- docker compose -f docker-compose-kong.yml up -d` → mismo API vía `http://127.0.0.1:8000/uco-parking/...` ([docs/KONG_UCOPARKING.md](docs/KONG_UCOPARKING.md)). En Infisical debe existir **`KONG_PG_PASSWORD`**.
5. **Front** (`D:\UcoParking-frontend\UcoParking`, misma rama `BaúlSecretos`): `npm run dev:secrets` (ver README del front).

Pasos detallados: [docs/PRUEBAS_CONEXION_FRONT.md](docs/PRUEBAS_CONEXION_FRONT.md). Auth0 back: [docs/AUTH0_BACKEND.md](docs/AUTH0_BACKEND.md).
