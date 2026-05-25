# Levanta Kong + Postgres con variables de Infisical (KONG_PG_PASSWORD obligatoria en el proyecto).
# Opcional — WAF ModSecurity: .\scripts\run-kong.ps1 --profile waf
# (los argumentos extra van entre el -f y "up", p. ej. --profile waf).
Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"
Set-Location (Join-Path $PSScriptRoot "..")
infisical run --env=dev -- docker compose -f docker-compose-kong.yml @args up -d
