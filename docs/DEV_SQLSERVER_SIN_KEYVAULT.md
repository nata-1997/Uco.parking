# SQL Server en desarrollo (sin Azure Key Vault)

## Comportamiento actual

- **`spring.profiles.active=dev`** (por defecto en `application.yml`).
- **Key Vault** queda **desactivado** (`spring.cloud.azure.keyvault.secret.enabled=false` en `application-dev.yml` y por defecto en `application.yml`).
- La **conexión JDBC** se define en `application.yml` con valores por defecto pensados para **SQL Server en Docker** en `localhost:1433`, base **`UCOParking`**, usuario **`sa`**, contraseña por defecto **`TuPassword#123`** (ajústala si la tuya es distinta).

## Variables de entorno (opcional)

| Variable        | Ejemplo por defecto | Descripción                          |
|-----------------|---------------------|--------------------------------------|
| `DB_HOST`       | `localhost`         | Host del contenedor SQL              |
| `DB_PORT`       | `1433`              | Puerto                               |
| `DB_NAME`       | `UCOParking`        | Nombre de la base                    |
| `DB_USERNAME`   | `sa`                | Usuario                              |
| `DB_PASSWORD`   | (ver `application.yml`) | Contraseña; usa comillas si tiene `#` |

## Archivo local de secretos (opcional)

1. Copia `src/main/resources/application-secrets.properties.example` a la **raíz del proyecto** como `application-secrets.properties` (ya está en `.gitignore`).
2. Ajusta usuario/contraseña ahí si no quieres usar el valor por defecto.
3. Con perfil **dev**, `application-dev.yml` importa **`optional:file:./application-secrets.properties`**: si el archivo no existe, no falla.

Ejecuta el back desde la **raíz del repo** (`mvn spring-boot:run`) para que `./application-secrets.properties` se resuelva bien.

## Cuando vuelvas a Key Vault (prod)

- Perfil **`prod`** (`application-prod.yml`): Key Vault con **Spring Cloud Azure** (`spring.cloud.azure.keyvault.secret.*`) y datasource con variables `DB-SERVER`, `DB-NAME`, etc.
- Para pruebas locales **sin** tocar prod, sigue usando solo **`dev`**.

## Cómo “deshacer” el modo local sin Key Vault

Cuando indiques, se puede: volver a obligar `DB_PASSWORD` sin default en `application.yml`, quitar el import opcional de secrets, o reactivar solo lectura de secretos desde Key Vault en dev.
