# Jaeger y trazas OpenTelemetry (UcoParking API)

El backend usa **Spring Boot 4** con `spring-boot-starter-opentelemetry`: las peticiones HTTP (y otras observaciones de Micrometer) se exportan en **OTLP** hacia un colector compatible (por defecto **Jaeger** en local).

## Arrancar Jaeger

Desde la raíz del repo del API:

```bash
docker compose -f docker-compose.jaeger.yml up -d
```

- **UI:** [http://localhost:16686](http://localhost:16686) → pestaña *Search* → servicio **UcoParking** (nombre de `spring.application.name`).
- **OTLP HTTP:** `http://localhost:4318/v1/traces` (valor por defecto en `application.yml`).
- **OTLP gRPC:** `localhost:4317` (puedes configurar el transporte gRPC en Spring si lo necesitas).

## Configurar el API

| Variable / propiedad | Descripción |
|----------------------|-------------|
| `OTEL_EXPORTER_OTLP_TRACES_ENDPOINT` | URL OTLP de trazas (sobrescribe el default `http://localhost:4318/v1/traces`). |
| `TRACE_SAMPLE_PROBABILITY` | Muestreo global (`0.0`–`1.0`). En **dev** el perfil fija `1.0` en `application-dev.yml`. |

Si Jaeger **no** está en marcha, el exportador puede registrar errores de conexión en logs; no afecta a las respuestas HTTP.

## Probar

1. Levanta Jaeger (arriba).
2. Arranca el API (`mvn spring-boot:run` o tu script habitual).
3. Llama a cualquier endpoint, por ejemplo `GET http://127.0.0.1:8080/uco-parking/api/v1/parking-spots`.
4. En la UI de Jaeger, busca el servicio **UcoParking** y el rango de tiempo actual.

Con **Kong** u otro proxy delante, la traza cubre el tramo que ejecuta Spring; ver propagación W3C (`traceparent`) si enlazas servicios adicionales.
