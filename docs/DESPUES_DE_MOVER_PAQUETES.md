# Después de mover paquetes Java (MapStruct / Spring)

## Error: `ConflictingBeanDefinitionException` … `ReserveParkingSpotMapperImpl`

MapStruct genera una clase `ReserveParkingSpotMapperImpl` y Spring la registra con el nombre de bean **`reserveParkingSpotMapperImpl`**.  
Ese nombre **no incluye el paquete**: si en el classpath siguen existiendo **dos clases** con el mismo nombre corto (por ejemplo una antigua en `…application.application…` y otra nueva en `…application…`), Spring detecta **dos definiciones incompatibles** con el **mismo nombre de bean**.

En el código fuente actual solo debe existir **un** paquete; el conflicto casi siempre viene de **salidas de compilación viejas** (`target/`, `out/` de IntelliJ).

## Solución (hazlo en este orden)

1. **Cierra la aplicación** si está en ejecución.

2. **Borra carpetas de compilación** en la raíz del proyecto UcoParcking:
   - `target/`
   - `out/` (si existe, típico de IntelliJ IDEA)

   En PowerShell, desde la raíz del repo:

   ```powershell
   Remove-Item -Recurse -Force .\target, .\out -ErrorAction SilentlyContinue
   ```

3. **Recompila solo con Maven** (evita mezclar salida del IDE con `target/`):

   ```bash
   mvn clean compile
   ```

   O para arrancar:

   ```bash
   mvn clean spring-boot:run
   ```

4. En **IntelliJ**: *Build → Rebuild Project* (después del paso 2), o activa *Delegate IDE build/run actions to Maven* en ajustes del proyecto para que no genere clases duplicadas en `out/`.

5. Si Maven **cacheó** un fallo de dependencias antiguo:

   ```bash
   mvn clean install -U
   ```

## Comprobación

Tras `mvn clean compile`, no debe existir ninguna ruta bajo `target/classes` como:

`…/parkingspot/application/application/…`

Solo debe aparecer:

`…/parkingspot/application/inputport/…/ReserveParkingSpotMapperImpl.class`
