# Cupos de parqueo (API interna)

Base URL del back (perfil `dev`, ver `application.yml`): context path **`/uco-parking`**.

Ejemplo: `GET http://localhost:8080/uco-parking/api/v1/parking-spots`

## Endpoints

| Método | Ruta | Descripción |
|--------|------|-------------|
| `GET` | `/api/v1/parking-spots` | Lista cupos. Query opcional: `forStudentId` (UUID del estudiante en sesión) para devolver `canRelease` en cada ítem. |
| `GET` | `/api/v1/parking-spots?forStudentId={uuid}` | Igual; `canRelease` es `true` si el estudiante es dueño del cupo (`reserved` u `occupied` con su `ReservedByStudentId`), o si el cupo está `OCCUPIED` sin dueño (demo antigua) para poder liberarlo desde el mapa. |
| `POST` | `/api/v1/parking-spots/{spotCode}/reserve` | Cuerpo: `{ "studentId": "uuid", "plate": "ABC12A", "startTime": "08:00", "endTime": "10:00" }`. **Placa:** 3 letras + 2 dígitos + 1 letra o dígito (6 caracteres). Horas interpretadas en **America/Bogota**; no se acepta `startTime` anterior al instante actual en esa zona. Máximo **2** reservas activas por estudiante (reservas `RESERVED` cuya hora de fin aún no ha pasado en Bogotá). Respuesta **204**. |
| `POST` | `/api/v1/parking-spots/{spotCode}/release` | Cuerpo: `{ "studentId": "uuid" }`. El dueño (`ReservedByStudentId`) puede liberar reservas/ocupaciones propias. Cupos `OCCUPIED` **sin** dueño (datos demo viejos) pueden liberarse con cualquier `studentId` válido. Respuesta **204**. |

## Cuerpo de lista (`GET`)

Cada elemento incluye: `id`, `status`, `plate`, `startTime`, `endTime`, `reservedByStudentId`, `canRelease`.

- **`status`**: valor en minúsculas para el front. Si el cupo está reservado y la hora actual (Bogotá) cae dentro de `[startTime, endTime]`, se expone como **`occupied`** aunque en base de datos siga `RESERVED` (así el mapa muestra el bloque ocupado durante la franja).
- **`canRelease`**: el front puede mostrar el botón de liberar solo cuando es `true` (requiere haber llamado el listado con `forStudentId` igual al usuario autenticado).

## Actualización periódica (front)

Para reflejar reservas de otros usuarios sin WebSocket, el cliente debe **volver a consultar** `GET /api/v1/parking-spots?forStudentId=...` cada cierto intervalo (por ejemplo 15–30 s) o al recuperar el foco de la ventana. El backend es reactivo (`Mono`) pero la fuente de verdad sigue siendo JPA; el polling es el patrón previsto hasta integrar push si se desea.

## Datos iniciales

`ParkingSpotBootstrap` inserta A1–B6 en **AVAILABLE** la primera vez que la tabla está vacía. Para bases ya pobladas con cupos ocupados de prueba, ejecuta `docs/reset-parking-spots-libres.sql`.

## Base de datos

Con perfil **`dev`**, `application-dev.yml` define `spring.jpa.hibernate.ddl-auto: update` para crear/actualizar la tabla `ParkingSpot` (incluye `ReservedByStudentId`). En otros perfiles mantén `ddl-auto: none` y aplica el script DDL / migración en `docs/sqlserver-ddl-UCOParking.sql`.

## Reactivo

El controlador HTTP devuelve `Mono` y delega JPA en `Schedulers.boundedElastic()` para no bloquear el hilo reactivo. La persistencia sigue siendo JPA (bloqueante), alineado al resto del proyecto.

## Registro de estudiantes

`POST /api/v1/students` sigue el mismo patrón reactivo (`Mono<ResponseEntity<RegisterNewStudentResponse>>`). El cuerpo JSON debe coincidir con `RegisterNewStudentRequest`: `academicProgram` e `idType` como **UUID** existentes en las tablas catálogo. El `studentId` usado en reservas debe ser un estudiante ya registrado.

## Correo al reservar (SendGrid)

Tras una reserva exitosa (`204`), el API intenta enviar un correo al estudiante con el **correo registrado en la tabla `Student`** (no el de Auth0, salvo que coincidan). Requiere variables `SENDGRID_API_KEY` y `SENDGRID_FROM_EMAIL` (o equivalentes en `application-secrets.properties`; ver `application-secrets.properties.example`). El remitente debe estar **verificado en SendGrid** (remitente único o dominio; si usas correo institucional vía Microsoft 365/Outlook, autentica ese dominio en SendGrid). Con `SENDGRID_ENABLED=false` o sin API key, el envío se omite y la reserva sigue funcionando.

Para pruebas locales con secretos del equipo, conviene un `.env` en la raíz del API (export desde Infisical o copia de `.env.example`): ver [SECRETOS_E_INFISICAL.md](SECRETOS_E_INFISICAL.md).
