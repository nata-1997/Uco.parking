# Copia de seguridad y restauración de UCOParking (Docker)

## Hacer un backup ahora

Con SQL en marcha y secretos (Infisical `dev`):

```bat
cd /d D:\Uco.parking
infisical run --env=dev -- scripts\backup-ucoparking.cmd
```

Se genera un archivo **`backups\UCOParking-AAAAMMDD-HHmmss.bak`** en la raíz del repo.

**Importante:** copia ese `.bak` a **otro sitio** (OneDrive, Google Drive, USB, otro disco). Si solo está en la misma máquina y el disco falla o borras la carpeta, pierdes el respaldo.

## Por qué se “pierde” la base otra vez

- `docker compose down -v` borra el **volumen** → se pierden los datos del contenedor.
- Sin copia externa, un backup solo en `backups/` cae en el mismo riesgo que el resto del PC.

## Restaurar desde un `.bak`

1. Para el API (Spring) si está conectado a la base.
2. Coloca el `.bak` en `backups\` o ten a mano la ruta completa.

Último backup en `backups\`:

```bat
infisical run --env=dev -- scripts\restore-ucoparking.cmd
```

Un archivo concreto (PowerShell):

```bat
infisical run --env=dev -- powershell -NoProfile -ExecutionPolicy Bypass -File scripts\restore-ucoparking.ps1 -BackupFile "D:\Uco.parking\backups\UCOParking-20260125-160000.bak"
```

La restauración **sobrescribe** la base `UCOParking` del contenedor.

## SSMS (opcional)

También puedes hacer **Backup / Restore** desde SQL Server Management Studio conectando a `localhost,1433` con `sa`. Los scripts del repo automatizan lo mismo vía `docker exec` + `sqlcmd`.
