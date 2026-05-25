# Levanta SQL Server (docker-compose.yml raíz) con MSSQL_SA_PASSWORD desde Infisical.
Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"
Set-Location (Join-Path $PSScriptRoot "..")
infisical run --env=dev -- docker compose up -d @args
