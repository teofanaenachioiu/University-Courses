create database MiniFacebook
go

use MiniFacebook
go

if OBJECT_ID('Likeuri','U') is not null --U=user defined table
	drop table Likeuri
if OBJECT_ID('Comentarii','U') is not null
	drop table Comentarii
if OBJECT_ID('Postari','U') is not null
	drop table Postari
if OBJECT_ID('Pagini','U') is not null
	drop table Pagini
if OBJECT_ID('Categorii','U') is not null
	drop table Categorii
if OBJECT_ID('Utilizatori','U') is not null
	drop table Utilizatori

create table Utilizatori 
	(
	CodU int primary key identity(1,1), -- 1: seed, 1: incrementul (1,1) sunt val default
	Nume varchar(30),
	Oras varchar(40),
	DataNasterii date
	)
go

create table Categorii(
	CodC int primary key identity(1,1),
	Denumire varchar (50),
	Descriere  varchar(300)
)
go

create table Pagini(
	CodP int primary key identity(1,1),
	Denumire varchar (50),
	CodC int references Categorii(CodC)
)

create table Postari(
	CodP int primary key identity(1,1),
	DataPublicarii date,
	ContinutText varchar(150),
	NrPartajari int, 
	CodU int references Utilizatori (CodU)
)
go

create table Comentarii(
	CodC int primary key identity(1,1),
	DataPublicarii date,
	ContinutText varchar (150),
	CodP int references Postari(CodP)
)
go

create table Likeuri(
	CodU int references Utilizatori(CodU),
	CodP int references Pagini(CodP),
	DataLike date,
	primary key(CodU, CodP)
)
go

insert into Utilizatori(Nume, Oras, DataNasterii) values 
('u1','o1','1-1-2000'),
('u2','o2','2-2-2002');

insert into Postari(DataPublicarii, ContinutText, NrPartajari, CodU) values
('1-1-2019','t1',1000,1)
go

select * from Utilizatori
select * from Postari

-- nonrepeatable read
begin tran
select * from Utilizatori where CodU=1

select * from Utilizatori where CodU=1
commit tran

-- repeatable read
set transaction isolation level repeatable read
begin tran
select * from Utilizatori where CodU=1

select * from Utilizatori where CodU=1
commit tran

-- creare index
sp_helpindex Utilizatori


declare @i int = 3;
while @i<=4000
begin
	insert into Utilizatori(Nume, Oras, DataNasterii) values ('u'+cast(@i as varchar(5)),'o1','1-1-2000');
	set @i = @i+1;
end

select * from Utilizatori where Nume = 'u753' -- CTRL+M, apoi F5 ca sa vad execution plan

create index IDX_NC_Nume on Utilizatori(Nume)