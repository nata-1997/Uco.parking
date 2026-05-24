# Cupos de parqueo (API interna)

Base URL del back (perfil `dev`, ver `application.yml`): context path **`/uco-parking`**.

Ejemplo: `GET http://localhost:8080/uco-parking/api/v1/parking-spots`

## Endpoints

| Método | Ruta | Descripción |
|--------|------|-------------|
| `GET` | `/api/v1/parking-spots` | Lista todos los cupos (`id`, `status` en minúsculas, `plate`, `startTime`, `endTime`). |
| `POST` | `/api/v1/parking-spots/{spotCode}/reserve` | Cuerpo JSON: `{ "plate": "ABC123", "startTime": "08:00", "endTime": "10:00" }`. Solo si el cupo está `available`. Respuesta **204**. |
| `POST` | `/api/v1/parking-spots/{spotCode}/release` | Libera el cupo si está `reserved` u `occupied` (vuelve a `available`). Respuesta **204**. |

## Datos iniciales

`ParkingSpotBootstrap` inserta A1–B6 la primera vez que la tabla está vacía (misma distribución que el mock del front: algunos `OCCUPIED`).

## Base de datos

Con perfil **`dev`**, `application-dev.yml` define `spring.jpa.hibernate.ddl-auto: update` para crear/actualizar la tabla `ParkingSpot`. En otros perfiles mantén `ddl-auto: none` y aplica script DDL manual si aplica.

## Reactivo

El controlador HTTP devuelve `Mono` y delega JPA en `Schedulers.boundedElastic()` para no bloquear el hilo reactivo. La persistencia sigue siendo JPA (bloqueante), alineado al resto del proyecto.

## Registro de estudiantes

`POST /api/v1/students` sigue el mismo patrón reactivo (`Mono<ResponseEntity<RegisterNewStudentResponse>>`). El cuerpo JSON debe coincidir con `RegisterNewStudentRequest`: `academicProgram` e `idType` como **UUID** existentes en las tablas catálogo.
