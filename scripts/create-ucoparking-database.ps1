# Crea la base UCOParking en el contenedor sqlserver-ucoparking (docker-compose.yml raíz).
# Requisitos: contenedor en marcha y MSSQL_SA_PASSWORD en el entorno.
# Con Infisical desde CMD use el wrapper: infisical run --env=dev -- scripts\create-ucoparking-database.cmd
Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"

$saPass = $env:MSSQL_SA_PASSWORD
if (-not $saPass) { $saPass = $env:SPRING_DATASOURCE_PASSWORD }
if (-not $saPass) { $saPass = $env:DB_PASSWORD }
if (-not $saPass) {
    Write-Error @"
Falta contraseña sa para sqlcmd. En Infisical define MSSQL_SA_PASSWORD, SPRING_DATASOURCE_PASSWORD o DB_PASSWORD.
Ejemplo: infisical run --env=dev -- scripts\create-ucoparking-database.cmd
"@
}

$exists = docker ps -q -f "name=sqlserver-ucoparking"
if (-not $exists) {
    Write-Error "No hay contenedor 'sqlserver-ucoparking'. Arranca SQL antes: infisical run --env=dev -- docker compose up -d"
}

$sqlcmd = "/opt/mssql-tools18/bin/sqlcmd"
# Una sola línea para evitar problemas de comillas entre PowerShell y docker exec.
$query = "IF NOT EXISTS (SELECT 1 FROM sys.databases WHERE name = N'UCOParking') CREATE DATABASE UCOParking;"

docker exec sqlserver-ucoparking $sqlcmd -S localhost -U sa -P $saPass -C -Q $query
if ($LASTEXITCODE -ne 0) {
    Write-Error "sqlcmd falló (código $LASTEXITCODE). Revisa contraseña SA y que el contenedor esté healthy."
}

Write-Host "Listo. Vuelve a arrancar Spring con infisical run --env=dev -- .\mvnw.cmd spring-boot:run"
