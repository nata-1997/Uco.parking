#!/bin/sh
# Registra en Kong (Admin API) el upstream hacia Spring Boot con context-path /uco-parking.
# Idempotente: 409 = ya existe (nombre de servicio o de ruta).
set -eu

ADMIN="${KONG_ADMIN:-http://kong:8001}"
UPSTREAM="${UCOPARKING_UPSTREAM:-http://host.docker.internal:8080}"

echo "Esperando Admin API en ${ADMIN}..."
i=0
while [ "$i" -lt 60 ]; do
  if curl -fsS "${ADMIN}/status" >/dev/null 2>&1; then
    break
  fi
  i=$((i + 1))
  sleep 2
done
if ! curl -fsS "${ADMIN}/status" >/dev/null 2>&1; then
  echo "Kong Admin no respondió a tiempo." >&2
  exit 1
fi

echo "Creando servicio ucoparking-backend -> ${UPSTREAM}"
sc="$(curl -sS -o /dev/null -w "%{http_code}" -X POST "${ADMIN}/services" \
  -H "Content-Type: application/json" \
  -d "{\"name\":\"ucoparking-backend\",\"url\":\"${UPSTREAM}\"}" || true)"
if [ "$sc" != "201" ] && [ "$sc" != "409" ]; then
  echo "POST /services falló con HTTP ${sc}" >&2
  exit 1
fi

echo "Creando ruta /uco-parking (strip_path=false)"
rc="$(curl -sS -o /dev/null -w "%{http_code}" -X POST "${ADMIN}/services/ucoparking-backend/routes" \
  -H "Content-Type: application/json" \
  -d '{"name":"ucoparking-route","paths":["/uco-parking"],"strip_path":false}' || true)"
if [ "$rc" != "201" ] && [ "$rc" != "409" ]; then
  echo "POST /routes falló con HTTP ${rc}" >&2
  exit 1
fi

echo "Listo. Desde tu máquina: http://127.0.0.1:8000/uco-parking/api/v1/..."
