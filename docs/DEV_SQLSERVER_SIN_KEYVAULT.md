# SQL Server en desarrollo (sin Azure Key Vault)

## Enfoque actual (rama de pruebas con Infisical)

- Perfil **`dev`**: Key Vault **desactivado** (`application-dev.yml`).
- **No** se usa `application-secrets.properties`: JDBC y Auth0 llegan por **variables de entorno** (inyectadas con `infisical run` u otro gestor).
- Recomendado en Infisical: `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD` (estándar Spring Boot).

## Variables alternativas (sin URL JDBC completa)

| Variable      | Descripción                |
|---------------|----------------------------|
| `DB_HOST`     | Host SQL Server            |
| `DB_PORT`     | Puerto                     |
| `DB_NAME`     | Nombre de la base          |
| `DB_USERNAME` | Usuario                    |
| `DB_PASSWORD` | Contraseña                 |

## Azure Key Vault (producción)

- Perfil **`prod`** (`application-prod.yml`): Key Vault con Spring Cloud Azure y datasource propio de prod.
- **Infisical** no sustituye a Key Vault en Azure: son sistemas distintos; en local/dev usamos Infisical; en Azure puedes seguir usando Key Vault.

Guía principal: [INFISICAL_UCOPARKING.md](INFISICAL_UCOPARKING.md).
