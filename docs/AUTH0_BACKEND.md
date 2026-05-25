# Backend Uco.parking + Auth0 (rama `auth0`)

## Variables de entorno

| Variable | Obligatoria si… | Ejemplo |
|----------|-------------------|---------|
| `AUTH0_ISSUER_URI` | Quieres proteger `/api/v1/**` con JWT | `https://TU-TENANT.us.auth0.com/` |
| `AUTH0_AUDIENCE` | Registraste un API en Auth0 y el access token incluye `aud` | Identificador del API en Auth0 |

Si **no** defines `AUTH0_ISSUER_URI`, el API sigue **abierto** en `/api/v1/**` (útil en local sin token).

Debe coincidir con el tenant del front (`VITE_AUTH0_DOMAIN` → issuer `https://<domain>/`).

## Front (alineado)

- Mismo tenant y (si aplica) **audience** que `VITE_AUTH0_AUDIENCE`.
- Enviar cabecera `Authorization: Bearer <access_token>` en las llamadas a `/uco-parking/api/v1/...`.
- El token debe incluir el claim **`email`** (scope `email` y/o Action en Auth0 que copie el email al access token).

## Reglas de autorización en el back

Con JWT activo:

- **GET** `/students/lookup?email=` → el `email` debe coincidir con el del JWT.
- **POST** `/students` (registro) → el `email` del cuerpo debe coincidir con el del JWT.
- **GET** `/parking-spots` con `forStudentId` → el estudiante en BD debe tener el mismo email que el JWT.
- **POST** reserva / liberación → el `studentId` del cuerpo debe ser de un estudiante cuyo email coincide con el JWT.

## Auth0 Dashboard

1. API (opcional): crear API, identificador = valor de `AUTH0_AUDIENCE` / `VITE_AUTH0_AUDIENCE`.
2. Aplicación SPA: autorizar la API; scopes recomendados: `openid profile email`.
3. Si el access token no trae `email`, usar una **Action** (login / post-login) que añada `email` al token para el API.
