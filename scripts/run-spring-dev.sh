#!/usr/bin/env sh
# Arranca Spring Boot con secretos del entorno "dev" de Infisical.
set -eu
cd "$(dirname "$0")/.."
infisical run --env=dev -- ./mvnw spring-boot:run "$@"
