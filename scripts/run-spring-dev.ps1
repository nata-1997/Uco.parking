# Arranca Spring Boot con secretos del entorno "dev" de Infisical.
# Requisitos: CLI de Infisical instalada y `infisical init` en esta raíz (ver docs/INFISICAL_UCOPARKING.md).
Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"
Set-Location (Join-Path $PSScriptRoot "..")
infisical run --env=dev -- .\mvnw.cmd spring-boot:run @args
