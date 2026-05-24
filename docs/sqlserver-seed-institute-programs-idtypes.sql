/*
  Catálogo UCO — Institute, AcademicProgram, IdType
  Base: UCOParking (ajusta USE si aplica)
  Ejecutar después de crear las tablas (sqlserver-ddl-UCOParking.sql).
*/

USE UCOParking;
GO

SET NOCOUNT ON;

/* ---------- Institute: UCO ---------- */
IF NOT EXISTS (SELECT 1 FROM dbo.Institute WHERE id = '131A66E5-7C99-474B-867C-77866B86BFFF')
BEGIN
    INSERT INTO dbo.Institute (id, Name)
    VALUES ('131A66E5-7C99-474B-867C-77866B86BFFF', N'UCO');
END
GO

/* ---------- AcademicProgram (InstituteId = UCO) ---------- */
DECLARE @InstituteId UNIQUEIDENTIFIER = '131A66E5-7C99-474B-867C-77866B86BFFF';

IF NOT EXISTS (SELECT 1 FROM dbo.AcademicProgram WHERE id = '3BC03461-6D8D-492E-BA45-3A573CB134E3')
    INSERT INTO dbo.AcademicProgram (id, InstituteId, Name)
    VALUES ('3BC03461-6D8D-492E-BA45-3A573CB134E3', @InstituteId, N'Ingeniería de Sistemas');

IF NOT EXISTS (SELECT 1 FROM dbo.AcademicProgram WHERE id = 'E8BC3452-8693-4E78-951C-420B04BFB3A1')
    INSERT INTO dbo.AcademicProgram (id, InstituteId, Name)
    VALUES ('E8BC3452-8693-4E78-951C-420B04BFB3A1', @InstituteId, N'Derecho');

IF NOT EXISTS (SELECT 1 FROM dbo.AcademicProgram WHERE id = 'D26DB5DF-948F-47AF-BC71-9C7AF4212538')
    INSERT INTO dbo.AcademicProgram (id, InstituteId, Name)
    VALUES ('D26DB5DF-948F-47AF-BC71-9C7AF4212538', @InstituteId, N'Administración de Empresas');
GO

/* ---------- IdType ---------- */
IF NOT EXISTS (SELECT 1 FROM dbo.IdType WHERE id = '906C13B8-02CC-4D84-9668-357E5C3C30B2')
    INSERT INTO dbo.IdType (id, name)
    VALUES ('906C13B8-02CC-4D84-9668-357E5C3C30B2', N'Cédula de ciudadanía');

IF NOT EXISTS (SELECT 1 FROM dbo.IdType WHERE id = '40BD2C4D-4CD9-452A-9B65-4655433187A7')
    INSERT INTO dbo.IdType (id, name)
    VALUES ('40BD2C4D-4CD9-452A-9B65-4655433187A7', N'Cédula de extranjería');

IF NOT EXISTS (SELECT 1 FROM dbo.IdType WHERE id = '61B9C3D0-F1B8-4249-87F2-5A8F531AE121')
    INSERT INTO dbo.IdType (id, name)
    VALUES ('61B9C3D0-F1B8-4249-87F2-5A8F531AE121', N'NIT');

IF NOT EXISTS (SELECT 1 FROM dbo.IdType WHERE id = 'DF6CF3DD-B823-4739-9794-7D8A1FFE40AD')
    INSERT INTO dbo.IdType (id, name)
    VALUES ('DF6CF3DD-B823-4739-9794-7D8A1FFE40AD', N'Tarjeta de identidad');

IF NOT EXISTS (SELECT 1 FROM dbo.IdType WHERE id = '40449A5C-822B-4B82-BAD4-9176283149E2')
    INSERT INTO dbo.IdType (id, name)
    VALUES ('40449A5C-822B-4B82-BAD4-9176283149E2', N'Pasaporte');
GO

PRINT N'Catálogo Institute / AcademicProgram / IdType cargado (si no existía).';
GO
