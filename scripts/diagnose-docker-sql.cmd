@echo off
chcp 65001 >nul
REM Uso con Infisical (evita "no es una aplicación Win32 válida" al ejecutar .ps1 directo):
REM   infisical run --env=dev -- scripts\diagnose-docker-sql.cmd
powershell.exe -NoProfile -ExecutionPolicy Bypass -File "%~dp0diagnose-docker-sql.ps1"
exit /b %ERRORLEVEL%
