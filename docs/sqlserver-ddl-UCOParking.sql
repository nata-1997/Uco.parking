/*
  UCOParking — script DDL para Microsoft SQL Server
  Base de datos: UCOParking (créala antes o descomenta CREATE DATABASE)

  Orden: Institute → AcademicProgram → IdType → Student → ParkingSpot
  Ajusta nombres de esquema si usas uno distinto de dbo.
*/

-- Opcional: crear base (ejecutar conectado a master)
-- CREATE DATABASE UCOParking;
-- GO
-- USE UCOParking;
-- GO

USE UCOParking;
GO

SET ANSI_NULLS ON;
SET QUOTED_IDENTIFIER ON;
GO

/* ---------- Institute ---------- */
IF OBJECT_ID(N'dbo.Institute', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.Institute (
        id   UNIQUEIDENTIFIER NOT NULL CONSTRAINT PK_Institute PRIMARY KEY,
        Name NVARCHAR(255)    NOT NULL
    );
END
GO

/* ---------- AcademicProgram ---------- */
IF OBJECT_ID(N'dbo.AcademicProgram', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.AcademicProgram (
        id           UNIQUEIDENTIFIER NOT NULL CONSTRAINT PK_AcademicProgram PRIMARY KEY,
        InstituteId  UNIQUEIDENTIFIER NOT NULL,
        Name         NVARCHAR(255)    NOT NULL,
        CONSTRAINT FK_AcademicProgram_Institute
            FOREIGN KEY (InstituteId) REFERENCES dbo.Institute (id)
    );
END
GO

/* ---------- IdType ---------- */
IF OBJECT_ID(N'dbo.IdType', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.IdType (
        id   UNIQUEIDENTIFIER NOT NULL CONSTRAINT PK_IdType PRIMARY KEY,
        name NVARCHAR(255)    NOT NULL
    );
END
GO

/* ---------- Student ---------- */
IF OBJECT_ID(N'dbo.Student', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.Student (
        id               UNIQUEIDENTIFIER NOT NULL CONSTRAINT PK_Student PRIMARY KEY,
        AcademicProgram  UNIQUEIDENTIFIER NOT NULL,
        IdType           UNIQUEIDENTIFIER NOT NULL,
        Name             NVARCHAR(255)    NOT NULL,
        LastName         NVARCHAR(255)    NOT NULL,
        IdNumber         NVARCHAR(64)     NOT NULL,
        eMail            NVARCHAR(255)    NOT NULL,
        MobileNumber     NVARCHAR(32)     NOT NULL,
        CONSTRAINT FK_Student_AcademicProgram
            FOREIGN KEY (AcademicProgram) REFERENCES dbo.AcademicProgram (id),
        CONSTRAINT FK_Student_IdType
            FOREIGN KEY (IdType) REFERENCES dbo.IdType (id)
    );
END
GO

/* ---------- ParkingSpot (reservas / mapa) ---------- */
IF OBJECT_ID(N'dbo.ParkingSpot', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.ParkingSpot (
        SpotCode  NVARCHAR(10)  NOT NULL CONSTRAINT PK_ParkingSpot PRIMARY KEY,
        Status    NVARCHAR(20)  NOT NULL,
        Plate     NVARCHAR(10)  NULL,
        StartTime NVARCHAR(5)   NULL,
        EndTime   NVARCHAR(5)   NULL,
        ReservedByStudentId UNIQUEIDENTIFIER NULL
    );
END
GO

IF COL_LENGTH(N'dbo.ParkingSpot', N'ReservedByStudentId') IS NULL
BEGIN
    ALTER TABLE dbo.ParkingSpot ADD ReservedByStudentId UNIQUEIDENTIFIER NULL;
END
GO

/*
  Valores de Status alineados con el back/front:
    AVAILABLE | RESERVED | OCCUPIED

  Datos iniciales de cupos (opcional; el bootstrap Java también inserta si la tabla está vacía):

INSERT INTO dbo.ParkingSpot (SpotCode, Status, Plate, StartTime, EndTime, ReservedByStudentId) VALUES
(N'A1', N'AVAILABLE', NULL, NULL, NULL, NULL),
(N'A2', N'AVAILABLE', NULL, NULL, NULL, NULL),
(N'A3', N'AVAILABLE', NULL, NULL, NULL, NULL),
(N'A4', N'AVAILABLE', NULL, NULL, NULL, NULL),
(N'A5', N'AVAILABLE', NULL, NULL, NULL, NULL),
(N'B1', N'AVAILABLE', NULL, NULL, NULL, NULL),
(N'B2', N'AVAILABLE', NULL, NULL, NULL, NULL),
(N'B3', N'AVAILABLE', NULL, NULL, NULL, NULL),
(N'B4', N'AVAILABLE', NULL, NULL, NULL, NULL),
(N'B5', N'AVAILABLE', NULL, NULL, NULL, NULL),
(N'B6', N'AVAILABLE', NULL, NULL, NULL, NULL);
*/
