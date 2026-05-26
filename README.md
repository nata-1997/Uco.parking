# Uco.parking
Proyecto Uco Software II

## Desarrollo con Infisical (rama `BaúlSecretos` y pruebas)

Los secretos viven en **Infisical** (entorno `dev`), no en archivos locales del repositorio. Guía: [docs/INFISICAL_UCOPARKING.md](docs/INFISICAL_UCOPARKING.md) y [docs/INFISICAL_RAMA_BAULSECRETOS.md](docs/INFISICAL_RAMA_BAULSECRETOS.md).

Opcional en local: copia [.env.example](.env.example) a `.env` si necesitas variables para Docker o pruebas sin Infisical; la API carga `.env` al arrancar. Detalle: [docs/SECRETOS_E_INFISICAL.md](docs/SECRETOS_E_INFISICAL.md).

1. **CLI:** [Infisical CLI](https://infisical.com/docs/cli/overview) → `infisical login` y en la raíz del repo `infisical init` (o copia [.infisical.json.example](.infisical.json.example) a `.infisical.json` y completa `workspaceId`).
2. **SQL + base + API en un solo comando (Windows, CMD):** `infisical run --env=dev -- scripts\dev-setup-and-run-api.cmd` → levanta Docker SQL, crea `UCOParking` si falta y arranca Spring. Detalle y por qué “desaparece” la base: [docs/DEV_API_SQL_Y_DATOS.md](docs/DEV_API_SQL_Y_DATOS.md).
3. **Por pasos:** SQL con `.\scripts\run-sqlserver.ps1` o `infisical run --env=dev -- docker compose up -d` → base `UCOParking` (ver [docs/PRUEBAS_CONEXION_FRONT.md](docs/PRUEBAS_CONEXION_FRONT.md)) → Spring con `.\scripts\run-spring-dev.ps1` o `infisical run --env=dev -- .\mvnw.cmd spring-boot:run` → API en `http://127.0.0.1:8080/uco-parking/api/v1`.
4. **Kong (opcional):** `.\scripts\run-kong.ps1` o `infisical run --env=dev -- docker compose -f docker-compose-kong.yml up -d` → mismo API vía `http://127.0.0.1:8000/uco-parking/...` ([docs/KONG_UCOPARKING.md](docs/KONG_UCOPARKING.md)). En Infisical debe existir **`KONG_PG_PASSWORD`**.
5. **WAF ModSecurity (opcional, delante de Kong):** `infisical run --env=dev -- docker compose -f docker-compose-kong.yml --profile waf up -d` → `http://127.0.0.1:9080/uco-parking/...` ([docs/MODSECURITY_UCOPARKING.md](docs/MODSECURITY_UCOPARKING.md)).
6. **Front** (`D:\UcoParking-frontend\UcoParking`, misma rama `BaúlSecretos`): `npm run dev:secrets` (ver README del front).

Pasos detallados: [docs/PRUEBAS_CONEXION_FRONT.md](docs/PRUEBAS_CONEXION_FRONT.md). Auth0 back: [docs/AUTH0_BACKEND.md](docs/AUTH0_BACKEND.md).
