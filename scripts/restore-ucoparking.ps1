# Restaura UCOParking desde un .bak generado con backup-ucoparking (sobrescribe la base si existe).
# Uso:
#   infisical run --env=dev -- scripts\restore-ucoparking.cmd
#   (usa el .bak mas reciente en backups/)
# O:
#   infisical run --env=dev -- powershell -File scripts\restore-ucoparking.ps1 -BackupFile "D:\ruta\UCOParking-20260101-120000.bak"
param(
    [string] $BackupFile = ""
)

Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"

$Root = (Resolve-Path (Join-Path $PSScriptRoot "..")).Path
$BackupDir = Join-Path $Root "backups"

if (-not $BackupFile) {
    $latest = Get-ChildItem -Path $BackupDir -Filter "UCOParking-*.bak" -ErrorAction SilentlyContinue |
        Sort-Object LastWriteTime -Descending |
        Select-Object -First 1
    if (-not $latest) {
        Write-Error "No hay archivos UCOParking-*.bak en backups\. Pasa -BackupFile 'ruta\completa.bak'"
    }
    $BackupFile = $latest.FullName
}

if (-not (Test-Path -LiteralPath $BackupFile)) {
    Write-Error "No existe el archivo: $BackupFile"
}

$saPass = $env:MSSQL_SA_PASSWORD
if (-not $saPass) { $saPass = $env:SPRING_DATASOURCE_PASSWORD }
if (-not $saPass) { $saPass = $env:DB_PASSWORD }
if (-not $saPass) {
    Write-Error "Falta contrasena sa. infisical run --env=dev -- scripts\restore-ucoparking.cmd"
}

$cid = docker ps -q -f "name=sqlserver-ucoparking"
if (-not $cid) {
    Write-Error "Levanta el contenedor: docker compose up -d"
}

$sqlcmd = "/opt/mssql-tools18/bin/sqlcmd"
$insideName = "restore_ucoparking.bak"
$insidePath = "/var/opt/mssql/backup/$insideName"

Write-Host "Copiando al contenedor: $BackupFile -> $insidePath" -ForegroundColor Cyan
docker exec sqlserver-ucoparking mkdir -p /var/opt/mssql/backup 2>$null | Out-Null
docker cp $BackupFile "sqlserver-ucoparking:$insidePath"
if ($LASTEXITCODE -ne 0) { Write-Error "docker cp al contenedor fallo." }

# Si la base existe, desconecta clientes (Spring) antes de restaurar.
$restoreSql = "DECLARE @d int = (SELECT COUNT(*) FROM sys.databases WHERE name = N'UCOParking'); IF @d > 0 ALTER DATABASE [UCOParking] SET SINGLE_USER WITH ROLLBACK IMMEDIATE; RESTORE DATABASE [UCOParking] FROM DISK = N'$insidePath' WITH REPLACE, RECOVERY; ALTER DATABASE [UCOParking] SET MULTI_USER;"

Write-Host "RESTORE (esto sobrescribe UCOParking en el contenedor)..." -ForegroundColor Yellow
docker exec sqlserver-ucoparking $sqlcmd -S localhost -U sa -P $saPass -C -Q $restoreSql
if ($LASTEXITCODE -ne 0) {
    Write-Error "RESTORE fallo. Si la base no existia, creala antes con create-ucoparking-database o revisa el .bak."
}

Write-Host "Restauracion terminada." -ForegroundColor Green
