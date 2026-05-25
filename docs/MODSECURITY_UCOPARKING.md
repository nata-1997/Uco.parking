# ModSecurity (WAF) y UcoParking

**Infisical:** el WAF **no añade ningún secreto nuevo** (ni contraseñas) al baúl. Las variables opcionales (`MODSEC_RULE_ENGINE`, `BLOCKING_PARANOIA`, etc.) son **ajustes**, no credenciales; puedes dejarlas solo en Docker Compose con sus valores por defecto.

[Nginx + ModSecurity + OWASP CRS](https://github.com/coreruleset/modsecurity-crs-docker) actúa como **proxy con WAF** delante de **Kong**: el cliente habla primero con el WAF y este reenvía a Kong, que a su vez llega a Spring.

## Flujo

```text
Cliente / Vite proxy  →  WAF (9080)  →  Kong (8000 interno)  →  Spring (8080 host)
```

## Arranque

1. Levanta el stack Kong como siempre (SQL, Spring, etc.): ver [KONG_UCOPARKING.md](KONG_UCOPARKING.md).
2. Incluye el perfil **`waf`**:

   ```bash
   infisical run --env=dev -- docker compose -f docker-compose-kong.yml --profile waf up -d
   ```

   Windows (PowerShell), equivalente al script del repo:

   ```powershell
   .\scripts\run-kong.ps1 --profile waf
   ```

Sin `--profile waf`, el compose se comporta como antes (solo Kong y bootstrap, sin contenedor WAF).

## URLs

| Uso | URL |
|-----|-----|
| Con Kong (sin WAF) | `http://127.0.0.1:8000/uco-parking/api/v1/...` |
| **Con Kong + WAF** | `http://127.0.0.1:9080/uco-parking/api/v1/...` |
| Admin API Kong | `http://127.0.0.1:8001` (sin pasar por el WAF) |

## Frontend (Vite)

En Infisical (`dev`), apunta el proxy de desarrollo al WAF cuando lo tengas activo:

```env
VITE_DEV_API_PROXY_TARGET=http://127.0.0.1:9080
```

Las rutas del front siguen siendo relativas (`/uco-parking/...`).

## Variables de entorno (tuning)

Puedes definirlas en Infisical o en el shell antes de `docker compose` (Compose las pasa al servicio).

| Variable | Descripción | Valor por defecto en Compose |
|----------|-------------|------------------------------|
| `MODSEC_RULE_ENGINE` | `DetectionOnly` solo registra; `On` bloquea según reglas CRS. | `DetectionOnly` |
| `BLOCKING_PARANOIA` | Nivel de paranoia CRS (1–4). Más alto = más estricto y falsos positivos. | `1` |
| `DETECTION_PARANOIA` | Paranoia solo para detección (CRSv4). | (hereda CRS) |
| `WAF_UPSTREAM_KONG` | URL interna del proxy Kong **vista desde el contenedor WAF**. | `http://kong:8000` |

En producción suele afinarse CRS (exclusiones por ruta o cabeceras) y pasar gradualmente de `DetectionOnly` a `On`.

## Imagen Docker

Se usa la imagen publicada **`owasp/modsecurity-crs:nginx`**. Para entornos donde no convenga un tag móvil, fija un tag estable del [repositorio CRS Docker](https://github.com/coreruleset/modsecurity-crs-docker#stable-tags) editando `docker-compose-kong.yml`.

## Auth0 y CORS

El **Origin** del navegador sigue siendo el del front; el WAF reenvía cabeceras a Kong y a Spring. La configuración CORS del back no cambia por añadir esta capa.
