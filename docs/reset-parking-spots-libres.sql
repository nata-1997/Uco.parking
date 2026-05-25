-- Deja todos los cupos en AVAILABLE y limpia placa, franja y dueño.
-- Úsalo en SQL Server (SSMS, Azure Data Studio o sqlcmd) contra la base UCOParking
-- cuando tengas cupos de prueba ocupados o reservados y quieras el mapa en cero.

USE UCOParking;
GO

UPDATE dbo.ParkingSpot
SET
    Status = N'AVAILABLE',
    Plate = NULL,
    StartTime = NULL,
    EndTime = NULL,
    ReservedByStudentId = NULL;
GO
