# Comprueba login SA con MSSQL_SA_PASSWORD y lista bases en sqlserver-ucoparking.
# Si falla: la contraseña no coincide con la del contenedor (a menudo tras cambiar Infisical sin recrear el volumen).
# Uso (PowerShell directo): .\scripts\diagnose-docker-sql.ps1
# Uso con Infisical en CMD (recomendado): infisical run --env=dev -- scripts\diagnose-docker-sql.cmd
#   (Infisical no puede ejecutar .ps1 como binario en Windows.)
Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"

# Misma contraseña que Spring: Infisical a veces solo define DB_* o SPRING_DATASOURCE_* (no MSSQL_SA_PASSWORD).
$script:SaPasswordSource = $null
$saPass = $env:MSSQL_SA_PASSWORD
if ($saPass) { $script:SaPasswordSource = "MSSQL_SA_PASSWORD" }
if (-not $saPass) {
    $saPass = $env:SPRING_DATASOURCE_PASSWORD
    if ($saPass) { $script:SaPasswordSource = "SPRING_DATASOURCE_PASSWORD" }
}
if (-not $saPass) {
    $saPass = $env:DB_PASSWORD
    if ($saPass) { $script:SaPasswordSource = "DB_PASSWORD" }
}
if (-not $saPass) {
    Write-Error @"
Falta contraseña del usuario sa para el contenedor Docker.
En Infisical (dev) define al menos uno de:
  - MSSQL_SA_PASSWORD (recomendado; mismo valor que usa docker compose al levantar SQL)
  - SPRING_DATASOURCE_PASSWORD
  - DB_PASSWORD

Ejecuta con secretos inyectados, por ejemplo:
  infisical run --env=dev -- scripts\diagnose-docker-sql.cmd
"@
}

$cid = docker ps -q -f "name=sqlserver-ucoparking"
if (-not $cid) {
    Write-Error "No hay contenedor sqlserver-ucoparking. Levanta SQL: infisical run --env=dev -- docker compose up -d"
}

$sqlcmd = "/opt/mssql-tools18/bin/sqlcmd"
$query = "SET NOCOUNT ON; SELECT name FROM sys.databases ORDER BY name;"

Write-Host "Probando login sa + listado de bases (contraseña desde $script:SaPasswordSource; valor no mostrado)..." -ForegroundColor Cyan
docker exec sqlserver-ucoparking $sqlcmd -S localhost -U sa -P $saPass -C -Q $query
$code = $LASTEXITCODE
if ($code -ne 0) {
    Write-Host ""
    Write-Host "El login SA falló (contraseña tomada de $script:SaPasswordSource)." -ForegroundColor Red
    Write-Host "Causa frecuente: el contenedor se creó con OTRA contraseña; el volumen de Docker guarda la SA antigua." -ForegroundColor Yellow
    Write-Host "Opciones: (1) Poner en Infisical la misma contraseña que al primer 'docker compose up', o" -ForegroundColor Yellow
    Write-Host "          (2) Borrar volumen y recrear (BORRA DATOS): docker compose down -v && docker compose up -d" -ForegroundColor Yellow
    exit $code
}

Write-Host ""
Write-Host "Si no ves UCOParking arriba, crea la base:" -ForegroundColor Green
Write-Host "  infisical run --env=dev -- scripts\create-ucoparking-database.cmd" -ForegroundColor Green
Write-Host ""
Write-Host "Spring usa DB_USERNAME/DB_PASSWORD o SPRING_DATASOURCE_* (estos últimos tienen prioridad)." -ForegroundColor Green
Write-Host "Usuario sa + contraseña deben coincidir con la que fijó el contenedor al crearse (MSSQL_SA_PASSWORD en compose)." -ForegroundColor Green
