@echo off
chcp 65001 >nul
cd /d "%~dp0.."
echo === Levantando SQL (docker compose) ===
docker compose up -d
if errorlevel 1 exit /b 1
echo === Esperando SQL y creando UCOParking ===
powershell.exe -NoProfile -ExecutionPolicy Bypass -File "%~dp0bootstrap-dev-database.ps1"
exit /b %ERRORLEVEL%
