# Backend Uco.parking + Auth0 (rama `auth0`)

## Variables de entorno

**No hay un archivo `AUTH0_AUDIENCE` en el código del back.** Es una **variable de entorno del sistema** (o de tu IDE al ejecutar Spring). El `pom` / `src` leen el valor así:

- En `application.yml` está mapeado como `uco.auth0.audience: ${AUTH0_AUDIENCE:}` (vacío = no validar `aud`).
- El **front** usa **`VITE_AUTH0_AUDIENCE`** (p. ej. definido en Infisical y expuesto con `infisical run` antes de `npm run dev`).

| Variable (back) | ¿Dónde se define? | Obligatoria si… | Ejemplo |
|-----------------|-------------------|-----------------|---------|
| `AUTH0_ISSUER_URI` | Env / Run Configuration | Quieres JWT en `/api/v1/**` (si no usas `AUTH0_DOMAIN`) | `https://TU-TENANT.us.auth0.com/` |
| `AUTH0_DOMAIN` | Env / Infisical | Mismo efecto que issuer: se usa `https://<AUTH0_DOMAIN>/` si `AUTH0_ISSUER_URI` está vacío | `TU-TENANT.us.auth0.com` |
| `AUTH0_AUDIENCE` | Env / Run Configuration | Quieres validar el claim `aud` del JWT (API en Auth0) | Mismo **Identifier** que creaste en Auth0 → APIs |

**Front (solo referencia):** `VITE_AUTH0_AUDIENCE` debe ser **el mismo string** que `AUTH0_AUDIENCE` en el back si usas API con audience en ambos lados.

Si **no** defines ni `AUTH0_ISSUER_URI` ni `AUTH0_DOMAIN`, el API sigue **abierto** en `/api/v1/**` (útil en local sin token).

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
