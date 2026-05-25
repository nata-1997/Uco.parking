@echo off
REM Arranque completo dev: SQL Docker + base UCOParking + API Spring Boot.
REM Uso (desde cualquier sitio, ajusta ruta si hace falta):
REM   cd /d D:\Uco.parking
REM   infisical run --env=dev -- scripts\dev-setup-and-run-api.cmd
chcp 65001 >nul
cd /d "%~dp0.."

echo === 1/3 SQL Server (Docker) ===
docker compose up -d
if errorlevel 1 (
    echo ERROR: docker compose up fallo. Comprueba MSSQL_SA_PASSWORD con Infisical.
    exit /b 1
)

echo === 2/3 Base UCOParking ===
powershell.exe -NoProfile -ExecutionPolicy Bypass -File "%~dp0bootstrap-dev-database.ps1"
if errorlevel 1 exit /b 1

echo === 3/3 API Spring Boot (Ctrl+C para detener) ===
call mvnw.cmd spring-boot:run
exit /b %ERRORLEVEL%
