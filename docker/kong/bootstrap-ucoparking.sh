#!/bin/sh
# Registra en Kong (Admin API) el upstream hacia Spring Boot con context-path /uco-parking.
# Idempotente: no repite POST de ruta si ya existe (evita ruido 409/duplicate en Postgres).
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

echo "Servicio ucoparking-backend -> ${UPSTREAM}"
sc="$(curl -sS -o /dev/null -w "%{http_code}" -X POST "${ADMIN}/services" \
  -H "Content-Type: application/json" \
  -d "{\"name\":\"ucoparking-backend\",\"url\":\"${UPSTREAM}\"}" || true)"
if [ "$sc" != "201" ] && [ "$sc" != "409" ]; then
  echo "POST /services falló con HTTP ${sc}" >&2
  exit 1
fi

# Alinear URL del upstream (útil si cambiaste UCOPARKING_UPSTREAM o el servicio ya existía).
patch_sc="$(curl -sS -o /dev/null -w "%{http_code}" -X PATCH "${ADMIN}/services/ucoparking-backend" \
  -H "Content-Type: application/json" \
  -d "{\"url\":\"${UPSTREAM}\"}" || true)"
if [ "$patch_sc" != "200" ] && [ "$patch_sc" != "201" ] && [ "$patch_sc" != "204" ]; then
  echo "PATCH /services/ucoparking-backend falló con HTTP ${patch_sc}" >&2
  exit 1
fi

echo "Ruta /uco-parking (strip_path=false)"
routes_json="$(curl -fsS "${ADMIN}/services/ucoparking-backend/routes" || true)"
if echo "$routes_json" | grep -qE '"name"[[:space:]]*:[[:space:]]*"ucoparking-route"'; then
  echo "La ruta ucoparking-route ya existe; no se vuelve a crear."
else
  rc="$(curl -sS -o /dev/null -w "%{http_code}" -X POST "${ADMIN}/services/ucoparking-backend/routes" \
    -H "Content-Type: application/json" \
    -d '{"name":"ucoparking-route","paths":["/uco-parking"],"strip_path":false}' || true)"
  if [ "$rc" != "201" ] && [ "$rc" != "409" ]; then
    echo "POST /routes falló con HTTP ${rc}" >&2
    exit 1
  fi
fi

echo "Listo. Desde tu máquina: http://127.0.0.1:8000/uco-parking/api/v1/..."
