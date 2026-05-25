# Por qué “desaparece” la base UCOParking y cómo levantar todo

## Por qué ya no está la base de datos

Con **SQL Server en Docker**, los datos (incluida la base `UCOParking`) viven en un **volumen nombrado** (`sqlserver_data` en el `docker-compose.yml` de la raíz).

La base **deja de existir** (o vuelves a un SQL “vacío”) si ocurre algo de esto:

| Causa | Qué pasa |
|--------|-----------|
| `docker compose down -v` | El flag **`-v`** borra los volúmenes. Se pierden **todas** las bases de ese contenedor. |
| Borrar el volumen a mano | En Docker Desktop o `docker volume rm ...`. |
| Contenedor nuevo sin volumen | Si cambias el nombre del proyecto/volumen o recreas el stack de otra forma y no reutilizas el mismo volumen. |
| Otra instancia de SQL | Si conectas SSMS a **SQL de Windows** y creaste la base ahí, **no** es la misma que la del contenedor Docker. |

**`docker compose down` sin `-v`** suele **conservar** el volumen: al hacer `up` de nuevo los datos siguen.

## Copia de seguridad (.bak)

Para no depender solo del volumen Docker, genera backups y guárdalos fuera del PC o en la nube: [BACKUP_UCOPARKING.md](BACKUP_UCOPARKING.md) y `scripts\backup-ucoparking.cmd`.

## Cómo correr la API con un solo flujo (recomendado)

Desde la raíz del repo (`D:\Uco.parking`), con **Infisical** (`dev`) y Docker en marcha:

```bat
infisical run --env=dev -- scripts\dev-setup-and-run-api.cmd
```

Ese script:

1. Levanta **SQL Server** con `docker compose up -d`.
2. **Espera** a que SQL acepte conexiones.
3. Crea la base **`UCOParking`** si no existe (misma lógica de contraseña que `create-ucoparking-database.ps1`).
4. Arranca la **API** con `mvnw.cmd spring-boot:run`.

**Secretos en Infisical** (mínimo habitual): `MSSQL_SA_PASSWORD` para Compose; para Spring al menos `DB_*` o `SPRING_DATASOURCE_*` alineados con el `sa` del contenedor (ver [INFISICAL_UCOPARKING.md](INFISICAL_UCOPARKING.md) y [TROUBLESHOOTING_SQL_DOCKER.md](TROUBLESHOOTING_SQL_DOCKER.md)).

## Solo SQL + base (sin arrancar la API)

```bat
infisical run --env=dev -- scripts\bootstrap-dev-database.cmd
```

## Diagnóstico

```bat
infisical run --env=dev -- scripts\diagnose-docker-sql.cmd
```
