# Levanta Kong + Postgres con variables de Infisical (KONG_PG_PASSWORD obligatoria en el proyecto).
Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"
Set-Location (Join-Path $PSScriptRoot "..")
infisical run --env=dev -- docker compose -f docker-compose-kong.yml up -d @args
