# Espera a SQL Server en Docker y crea la base UCOParking si falta.
# Ejecutar con secretos en el entorno, p. ej.:
#   infisical run --env=dev -- scripts\bootstrap-dev-database.cmd
Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"

$Root = (Resolve-Path (Join-Path $PSScriptRoot "..")).Path
Set-Location $Root

$saPass = $env:MSSQL_SA_PASSWORD
if (-not $saPass) { $saPass = $env:SPRING_DATASOURCE_PASSWORD }
if (-not $saPass) { $saPass = $env:DB_PASSWORD }
if (-not $saPass) {
    Write-Error "Falta contrasena sa: define en Infisical MSSQL_SA_PASSWORD, SPRING_DATASOURCE_PASSWORD o DB_PASSWORD."
}

$cid = docker ps -q -f "name=sqlserver-ucoparking"
if (-not $cid) {
    Write-Error "No hay contenedor sqlserver-ucoparking. Ejecuta antes: docker compose up -d (con Infisical para MSSQL_SA_PASSWORD)."
}

$sqlcmd = "/opt/mssql-tools18/bin/sqlcmd"
Write-Host "Esperando a que SQL Server acepte conexiones (hasta ~90 s)..." -ForegroundColor Cyan
$ready = $false
for ($i = 0; $i -lt 45; $i++) {
    docker exec sqlserver-ucoparking $sqlcmd -S localhost -U sa -P $saPass -C -Q "SELECT 1" 2>$null | Out-Null
    if ($LASTEXITCODE -eq 0) {
        $ready = $true
        break
    }
    Start-Sleep -Seconds 2
}
if (-not $ready) {
    Write-Error "SQL Server no respondio a tiempo. Revisa logs: docker logs sqlserver-ucoparking"
}

Write-Host "Creando base UCOParking si no existe..." -ForegroundColor Cyan
& (Join-Path $PSScriptRoot "create-ucoparking-database.ps1")
Write-Host "Base lista. Puedes arrancar Spring: infisical run --env=dev -- .\mvnw.cmd spring-boot:run" -ForegroundColor Green
