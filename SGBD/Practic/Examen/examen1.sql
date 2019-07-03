-- Aprovizionare
create database Aprovizionare
go
use Aprovizionare
go
-- drop database Aprovizionare
go

if OBJECT_ID('Livrari', 'U') IS NOT NULL
	DROP TABLE Livrari
if OBJECT_ID('Legume', 'U') IS NOT NULL
	DROP TABLE Legume
if OBJECT_ID('TipuriLegume', 'U') IS NOT NULL
	DROP TABLE TipuriLegume 
if OBJECT_ID('Vanzatori', 'U') IS NOT NULL
	DROP TABLE Vanzatori
if OBJECT_ID('Aprozaruri', 'U') IS NOT NULL
	DROP TABLE Aprozaruri

--
create table Aprozaruri(
	Aid int primary key identity(1,1),
	Denumire VARCHAR(50),
	Strada varchar(50),
	Numar int,
	Stele int
)

create table TipuriLegume(
	Tid int primary key identity(1,1),
	Tip VARCHAR(50), -- radacinoase, de frunze, ...
	Descriere varchar(50),
	Numar int 
)

create table Legume(
	Lid int primary key identity(1,1),
	Denumire VARCHAR(50),
	TaraOrigine varchar(50),
	Sezon bit, -- este sau nu de sezon 
	Tid int foreign key references TipuriLegume(Tid)
)

create table Livrari( -- LegumeAprozaruri
	Lid int references Legume(Lid),
	Aid int references Aprozaruri(Aid),
	Cantitate int,
	Pret float, 
	CONSTRAINT pk_Livrari PRIMARY KEY(Lid, Aid)
)

create table Vanzatori(
	Vid int primary key identity(1,1),
	Nume varchar(50),
	Vechime int,
	Salar int,
	Aid int foreign key references Aprozaruri(Aid)
)

GO

INSERT INTO Aprozaruri VALUES ('A1', 'Republicii', 45, 3), ('A2', 'Padis', 4, 4)
INSERT INTO TipuriLegume VALUES ('frunzifere', 'pentru frunze', 4), ('radacinoase', 'pentru consumul radacinilor', 10), ('zarzavaturi', 'cruditati bune', 16)
INSERT INTO Legume VALUES ('Varza', 'Romania', 1, 3), ('patrunjel', 'Olanda', 1, 1)
INSERT INTO Livrari VALUES (1, 1, 10, 4), (1, 2, 12, 5)
INSERT INTO Vanzatori VALUES ('Dan', 12, 1200, 1), ('Mihai', 5, 2000, 2)
GO

select * from Aprozaruri
select * from TipuriLegume
select * from Legume
select * from Livrari
select * from Vanzatori

---

delete from Legume where Denumire = 'Telina'




-------------------- PHANTOM READS --------------------

BEGIN TRAN
WAITFOR DELAY '00:00:04'
INSERT INTO Legume VALUES ('Telina', 'Romania', 1, 1)
COMMIT TRAN




-------------- INDEX PENTRU TABELA LEGUME -------------

create index idx_tara_legume on Legume(TaraOrigine, Denumire)

select TaraOrigine, Denumire as 'Nr Legume' 
from Legume 