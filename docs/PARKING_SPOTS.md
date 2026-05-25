# Cupos de parqueo (API interna)

Base URL del back (perfil `dev`, ver `application.yml`): context path **`/uco-parking`**.

Ejemplo: `GET http://localhost:8080/uco-parking/api/v1/parking-spots`

## Endpoints

| Método | Ruta | Descripción |
|--------|------|-------------|
| `GET` | `/api/v1/parking-spots` | Lista cupos. Query opcional: `forStudentId` (UUID del estudiante en sesión) para devolver `canRelease` en cada ítem. |
| `GET` | `/api/v1/parking-spots?forStudentId={uuid}` | Igual; cada cupo incluye `reservedByStudentId` si hay reserva y `canRelease` solo si ese estudiante es el dueño y el cupo está `reserved` u `occupied` persistido. |
| `POST` | `/api/v1/parking-spots/{spotCode}/reserve` | Cuerpo: `{ "studentId": "uuid", "plate": "ABC123", "startTime": "08:00", "endTime": "10:00" }`. Horas interpretadas en **America/Bogota**; no se acepta `startTime` anterior al instante actual en esa zona. Máximo **2** reservas activas por estudiante (reservas `RESERVED` cuya hora de fin aún no ha pasado en Bogotá). Respuesta **204**. |
| `POST` | `/api/v1/parking-spots/{spotCode}/release` | Cuerpo: `{ "studentId": "uuid" }`. Solo el estudiante dueño de la reserva (`ReservedByStudentId`) puede liberar. Respuesta **204**. |

## Cuerpo de lista (`GET`)

Cada elemento incluye: `id`, `status`, `plate`, `startTime`, `endTime`, `reservedByStudentId`, `canRelease`.

- **`status`**: valor en minúsculas para el front. Si el cupo está reservado y la hora actual (Bogotá) cae dentro de `[startTime, endTime]`, se expone como **`occupied`** aunque en base de datos siga `RESERVED` (así el mapa muestra el bloque ocupado durante la franja).
- **`canRelease`**: el front puede mostrar el botón de liberar solo cuando es `true` (requiere haber llamado el listado con `forStudentId` igual al usuario autenticado).

## Actualización periódica (front)

Para reflejar reservas de otros usuarios sin WebSocket, el cliente debe **volver a consultar** `GET /api/v1/parking-spots?forStudentId=...` cada cierto intervalo (por ejemplo 15–30 s) o al recuperar el foco de la ventana. El backend es reactivo (`Mono`) pero la fuente de verdad sigue siendo JPA; el polling es el patrón previsto hasta integrar push si se desea.

## Datos iniciales

`ParkingSpotBootstrap` inserta A1–B6 la primera vez que la tabla está vacía (misma distribución que el mock del front: algunos `OCCUPIED` sin `ReservedByStudentId`; esos cupos no son liberables por estudiantes vía API).

## Base de datos

Con perfil **`dev`**, `application-dev.yml` define `spring.jpa.hibernate.ddl-auto: update` para crear/actualizar la tabla `ParkingSpot` (incluye `ReservedByStudentId`). En otros perfiles mantén `ddl-auto: none` y aplica el script DDL / migración en `docs/sqlserver-ddl-UCOParking.sql`.

## Reactivo

El controlador HTTP devuelve `Mono` y delega JPA en `Schedulers.boundedElastic()` para no bloquear el hilo reactivo. La persistencia sigue siendo JPA (bloqueante), alineado al resto del proyecto.

## Registro de estudiantes

`POST /api/v1/students` sigue el mismo patrón reactivo (`Mono<ResponseEntity<RegisterNewStudentResponse>>`). El cuerpo JSON debe coincidir con `RegisterNewStudentRequest`: `academicProgram` e `idType` como **UUID** existentes en las tablas catálogo. El `studentId` usado en reservas debe ser un estudiante ya registrado.
