create database Ceainarie
go
use Ceainarie 
go
create table MARCI(
	Mid int primary key identity, --sa se autocompleteze
	Nume varchar(50),
	Rating int not null
)
create table INGREDIENTE(
	Iid int primary key identity,
	Nume varchar(50) default 'Apa',
	Origine varchar(20),
	Cantitate int default 1 
)
create table CEAIURI(
	Cid int primary key identity,
	Nume varchar(50),
	Temperatura int check (Temperatura >=0 and Temperatura <=100),
	Mid int foreign key references MARCI(Mid)
)
create table CONTINUTURI(
	Cid int foreign key references CEAIURI(Cid),
	Iid int foreign key references INGREDIENTE(Iid),
	CantitateC int,
	constraint pk_Continuturi primary key(Cid,Iid) 
)
create table CODULDEBARE(
	COid int foreign key references CEAIURI(Cid),
	NrL int,
	constraint pk_Cod primary key(COid)
)

--drop database Ceainarie