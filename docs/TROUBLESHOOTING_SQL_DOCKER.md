# SQL Server en Docker — errores frecuentes

## `Cannot open database "UCOParking" … The login failed`

Ya hay **conexión TCP** al servidor; el fallo es **credenciales** o **la base no existe en ese contenedor**.

### 1. Misma contraseña en todo

El `sa` del contenedor es el que se fijó la **primera vez** que se creó el volumen con `MSSQL_SA_PASSWORD`.

En Infisical (`dev`), alinea:

| Uso | Secreto |
|-----|--------|
| Docker Compose | `MSSQL_SA_PASSWORD` |
| Spring (usuario `sa`) | `DB_PASSWORD` **o** `SPRING_DATASOURCE_PASSWORD` |

Deben ser **el mismo valor** que el `sa` del contenedor.

Si **cambiaste** `MSSQL_SA_PASSWORD` en Infisical **después** de haber levantado SQL la primera vez, el contenedor **sigue usando la contraseña antigua** guardada en el volumen. Entonces Spring (con la contraseña nueva) falla aunque el contenedor esté “Running”.

**Solución A (conservar datos):** en Infisical, vuelve a poner en `DB_PASSWORD` / `SPRING_DATASOURCE_PASSWORD` la contraseña **original** con la que se creó el volumen.

**Solución B (empezar de cero, se pierden datos del contenedor):**

```powershell
cd D:\Uco.parking
docker compose down -v
infisical run --env=dev -- docker compose up -d
infisical run --env=dev -- scripts\create-ucoparking-database.cmd
```

Luego arranca Spring con `infisical run --env=dev -- .\mvnw.cmd spring-boot:run`.

### 2. La base no existe en el contenedor

Si recreaste el volumen o nunca ejecutaste el script:

```bat
infisical run --env=dev -- scripts\create-ucoparking-database.cmd
```

### 3. Diagnóstico rápido

Desde **CMD** (recomendado con Infisical; en Windows Infisical no ejecuta `.ps1` como programa):

```bat
infisical run --env=dev -- scripts\diagnose-docker-sql.cmd
```

Desde **PowerShell** ya abierto:

```powershell
infisical run --env=dev -- powershell -NoProfile -ExecutionPolicy Bypass -File .\scripts\diagnose-docker-sql.ps1
```

- Si **falla el login**: `MSSQL_SA_PASSWORD` no coincide con el `sa` real del contenedor (punto 1).
- Si **lista bases pero no sale `UCOParking`**: ejecuta `scripts\create-ucoparking-database.cmd` (con Infisical como arriba).

### 4. `SPRING_DATASOURCE_URL` vs `DB_*`

Si en Infisical existe **`SPRING_DATASOURCE_URL`** (URL JDBC completa), Spring la usa y **puede ignorar** `DB_HOST` / `DB_PORT` del `application.yml`. Revisa que esa URL tenga:

- host y puerto del Docker (`localhost`, `1433` si mapeas así),
- `databaseName=UCOParking` (o equivalente),
- usuario/contraseña coherentes con el `sa` del contenedor.

## `Connection refused` en `localhost:1433`

Nadie escucha en ese puerto: contenedor parado, Docker reiniciado o el puerto no está publicado. Comprueba con `docker ps` y vuelve a `docker compose up -d`.
