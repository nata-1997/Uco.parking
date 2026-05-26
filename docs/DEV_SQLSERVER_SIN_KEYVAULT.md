# SQL Server en desarrollo (sin Azure Key Vault)

## Comportamiento actual

- **`spring.profiles.active=dev`** (por defecto en `application.yml`).
- **Key Vault** queda **desactivado** (`spring.cloud.azure.keyvault.secret.enabled=false` en `application-dev.yml` y por defecto en `application.yml`).
- La **conexión JDBC** puede venir de variables `SPRING_DATASOURCE_*` (p. ej. `.env` exportado desde Infisical), de `DB_*` en `application.yml`, o de `application-secrets.properties` en la raíz. Si no defines nada, la URL por defecto apunta a **SQL Server en Docker** en `localhost:1433` y base **`UCOParking`** con usuario **`sa`**; la **contraseña** debes definirla (`SPRING_DATASOURCE_PASSWORD`, `DB_PASSWORD` o `spring.datasource.password` en secrets), coherente con `MSSQL_SA_PASSWORD` de Docker.

## Variables de entorno (opcional)

| Variable        | Ejemplo por defecto | Descripción                          |
|-----------------|---------------------|--------------------------------------|
| `SPRING_DATASOURCE_URL` | — | JDBC completo (prioridad sobre la URL armada con `DB_*`). |
| `SPRING_DATASOURCE_USERNAME` | — | Usuario SQL Server. |
| `SPRING_DATASOURCE_PASSWORD` | — | Contraseña SQL Server. |
| `DB_HOST`       | `localhost`         | Host del contenedor SQL              |
| `DB_PORT`       | `1433`              | Puerto                               |
| `DB_NAME`       | `UCOParking`        | Nombre de la base                    |
| `DB_USERNAME`   | `sa`                | Usuario                              |
| `DB_PASSWORD`   | (definir en `.env` o secrets) | Contraseña; usa comillas en `.env` si tiene `#` |

## Archivo local de secretos (opcional)

1. Copia `src/main/resources/application-secrets.properties.example` a la **raíz del proyecto** como `application-secrets.properties` (ya está en `.gitignore`).
2. Ajusta usuario/contraseña ahí si no quieres usar el valor por defecto.
3. Con perfil **dev**, `application-dev.yml` importa **`optional:file:./application-secrets.properties`**: si el archivo no existe, no falla.

Ejecuta el back desde la **raíz del repo** (`mvn spring-boot:run`) para que `./application-secrets.properties` se resuelva bien.

Infisical, `.env` y prioridad de variables: [SECRETOS_E_INFISICAL.md](SECRETOS_E_INFISICAL.md).

## Cuando vuelvas a Key Vault (prod)

- Perfil **`prod`** (`application-prod.yml`): Key Vault con **Spring Cloud Azure** (`spring.cloud.azure.keyvault.secret.*`) y datasource con variables `DB-SERVER`, `DB-NAME`, etc.
- Para pruebas locales **sin** tocar prod, sigue usando solo **`dev`**.

## Cómo “deshacer” el modo local sin Key Vault

Cuando indiques, se puede: volver a obligar `DB_PASSWORD` sin default en `application.yml`, quitar el import opcional de secrets, o reactivar solo lectura de secretos desde Key Vault en dev.
