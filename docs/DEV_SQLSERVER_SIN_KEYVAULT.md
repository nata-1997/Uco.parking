# SQL Server en desarrollo (sin Azure Key Vault)

## Enfoque actual (rama de pruebas con Infisical)

- Perfil **`dev`**: Key Vault **desactivado** (`application-dev.yml`).
- **Recomendado:** JDBC y Auth0 por **variables de entorno** (inyectadas con `infisical run` u otro gestor). En Infisical: `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD` (estándar Spring Boot).
- **Opcional:** `application-secrets.properties` en la raíz del repo (ver `application-secrets.properties.example`) o `.env` para contraseña de BD u otras claves locales.
- Si no defines JDBC completo, la URL por defecto apunta a **SQL Server en Docker** en `localhost:1433` y base **`UCOParking`** con usuario **`sa`**; la **contraseña** debes definirla (`SPRING_DATASOURCE_PASSWORD`, `DB_PASSWORD` o `spring.datasource.password` en secrets), coherente con `MSSQL_SA_PASSWORD` de Docker.

## Variables alternativas (sin URL JDBC completa)

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

## Azure Key Vault (producción)

- Perfil **`prod`** (`application-prod.yml`): Key Vault con Spring Cloud Azure y datasource propio de prod.
- **Infisical** no sustituye a Key Vault en Azure: son sistemas distintos; en local/dev usamos Infisical; en Azure puedes seguir usando Key Vault.

Guía principal: [INFISICAL_UCOPARKING.md](INFISICAL_UCOPARKING.md).

Ejecuta el back desde la **raíz del repo** (`mvn spring-boot:run`) para que `./application-secrets.properties` se resuelva bien si lo usas.

Infisical, `.env` y prioridad de variables: [SECRETOS_E_INFISICAL.md](SECRETOS_E_INFISICAL.md).

## Cuando vuelvas a Key Vault (prod)

- Perfil **`prod`** (`application-prod.yml`): Key Vault con **Spring Cloud Azure** (`spring.cloud.azure.keyvault.secret.*`) y datasource con variables `DB-SERVER`, `DB-NAME`, etc.
- Para pruebas locales **sin** tocar prod, sigue usando solo **`dev`**.

## Cómo “deshacer” el modo local sin Key Vault

Cuando indiques, se puede: volver a obligar `DB_PASSWORD` sin default en `application.yml`, quitar el import opcional de secrets, o reactivar solo lectura de secretos desde Key Vault en dev.
