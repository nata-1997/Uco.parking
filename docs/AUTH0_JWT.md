# Auth0 + Spring (resource server)

1. En **Auth0**, crea una **API** y anota el **Identifier** (audience).
2. En la **SPA**, usa el mismo valor en `VITE_AUTH0_AUDIENCE` para que el access token lleve `aud` adecuado.
3. En el **backend**, define el issuer (mismo tenant que el front):

   - Variable de entorno o `application-secrets.properties`:
     - `spring.security.oauth2.resourceserver.jwt.issuer-uri=https://TU-TENANT.auth0.com/`
   - O `AUTH0_ISSUER_URI` si lo mapeas por configuración externa.

Si **no** defines `issuer-uri`, el `ResourceServerSecurityConfiguration` deja **todo el tráfico permitido** (útil en local sin Auth0). En **producción** (`application-prod.yml`) el issuer es obligatorio.

Las rutas bajo `/api/v1/**` exigen `Authorization: Bearer` cuando el resource server JWT está activo.

El proyecto incluye **`spring-boot-starter-web`** (MVC + servlet + `HttpSecurity`). **No** se usa `spring-boot-starter-webflux` (evitaría registrar `HttpSecurity`); solo la librería **`spring-webflux`** para devolver `Mono`/`Flux` desde `@RestController`.

`spring.main.web-application-type=servlet` fija el tipo de aplicación web.
