# Rama `BaúlSecretos` — pruebas con Infisical

Esta rama está pensada para **no mezclar** el flujo antiguo (archivos locales con contraseñas) con el día a día del equipo.

## Backend (`Uco.parking`)

1. `infisical login` y en la raíz del repo: `infisical init` (o copia `.infisical.json.example` → `.infisical.json` y ajusta `workspaceId`).
2. Opcional: en `.infisical.json`, usa `gitBranchToEnvironmentMapping` para enlazar la rama Git con el entorno Infisical (p. ej. `BaúlSecretos` → `dev`).
3. Arranque:
   - `.\scripts\run-spring-dev.ps1` (Windows), o
   - `infisical run --env=dev -- .\mvnw.cmd spring-boot:run`
4. Kong: `.\scripts\run-kong.ps1` o `infisical run --env=dev -- docker compose -f docker-compose-kong.yml up -d`  
   Debe existir el secreto **`KONG_PG_PASSWORD`** en Infisical (Compose lo exige).

## Frontend (`UcoParking-frontend/UcoParking`)

1. En la raíz **`UcoParking-frontend`**, archivo **`.infisical.json`**: pega el **Project ID** de Infisical en `workspaceId` (sustituye el placeholder) o ejecuta `infisical init` ahí.
2. Rama **`BaúlSecretos`**: `cd UcoParking` → `npm run dev:secrets`.
Integrar en `main` solo cuando el flujo esté validado.
