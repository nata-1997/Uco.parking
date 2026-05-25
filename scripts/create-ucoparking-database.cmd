@echo off
chcp 65001 >nul
REM Uso: infisical run --env=dev -- scripts\create-ucoparking-database.cmd
powershell.exe -NoProfile -ExecutionPolicy Bypass -File "%~dp0create-ucoparking-database.ps1"
exit /b %ERRORLEVEL%
