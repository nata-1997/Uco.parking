# Copia de seguridad FULL de UCOParking (SQL Server en Docker) -> carpeta backups/ del repo (no va a Git).
# Uso: infisical run --env=dev -- scripts\backup-ucoparking.cmd
Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"

$Root = (Resolve-Path (Join-Path $PSScriptRoot "..")).Path
$BackupDir = Join-Path $Root "backups"
New-Item -ItemType Directory -Force -Path $BackupDir | Out-Null

$saPass = $env:MSSQL_SA_PASSWORD
if (-not $saPass) { $saPass = $env:SPRING_DATASOURCE_PASSWORD }
if (-not $saPass) { $saPass = $env:DB_PASSWORD }
if (-not $saPass) {
    Write-Error "Falta contrasena (MSSQL_SA_PASSWORD, SPRING_DATASOURCE_PASSWORD o DB_PASSWORD). Usa: infisical run --env=dev -- scripts\backup-ucoparking.cmd"
}

$cid = docker ps -q -f "name=sqlserver-ucoparking"
if (-not $cid) {
    Write-Error "Contenedor sqlserver-ucoparking no esta en marcha. Ejecuta: infisical run --env=dev -- docker compose up -d"
}

$sqlcmd = "/opt/mssql-tools18/bin/sqlcmd"
$insidePath = "/var/opt/mssql/backup/UCOParking_work.bak"
$stamp = Get-Date -Format "yyyyMMdd-HHmmss"
$hostFile = Join-Path $BackupDir "UCOParking-$stamp.bak"

docker exec sqlserver-ucoparking mkdir -p /var/opt/mssql/backup 2>$null | Out-Null

# BACKUP con INIT sobre archivo de trabajo dentro del contenedor; luego copiamos al host con nombre con fecha.
$q = "BACKUP DATABASE [UCOParking] TO DISK = N'$insidePath' WITH FORMAT, INIT, NAME = N'UcoParking-full', SKIP, NOREWIND, NOUNLOAD, STATS = 10;"
Write-Host "Generando backup en el contenedor..." -ForegroundColor Cyan
docker exec sqlserver-ucoparking $sqlcmd -S localhost -U sa -P $saPass -C -Q $q
if ($LASTEXITCODE -ne 0) {
    Write-Error "BACKUP DATABASE fallo. Comprueba que la base UCOParking exista (scripts\create-ucoparking-database.cmd)."
}

Write-Host "Copiando a: $hostFile" -ForegroundColor Cyan
docker cp "sqlserver-ucoparking:$insidePath" $hostFile
if ($LASTEXITCODE -ne 0) {
    Write-Error "docker cp fallo."
}

Write-Host "Listo. Guarda este archivo en un lugar seguro (OneDrive, USB, otro disco)." -ForegroundColor Green
Write-Host $hostFile
