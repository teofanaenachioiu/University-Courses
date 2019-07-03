create database ChristmasGift
go

use ChristmasGift
go

if OBJECT_ID('Gifts','U') is not null
	drop table Gifts
if OBJECT_ID('Persons','U') is not null --U=user defined table
	drop table Persons
if OBJECT_ID('Categories','U') is not null
	drop table Categories
if OBJECT_ID('Products','U') is not null
	drop table Products
if OBJECT_ID('GiftProduct','U') is not null
	drop table Categorii

create table Gifts(
	gid int primary key identity(1,1),
	mess varchar(100)
)

create table Persons(
	pid int primary key identity(1,1),
	nume varchar(30),
	bday date,
	gid int foreign key references Gifts(gid)
	)

create table Categories(
	cid int primary key identity(1,1),
	nume varchar(30)
)

create table Products(
	pid int primary key identity(1,1),
	nume varchar(30),
	descriere varchar(100),
	cid int foreign key references Categories(cid)
)

create table GiftProduct(
	gid int foreign key references Gifts(gid),
	pid int foreign key references Products(pid),
	primary key(gid, pid),
	cantitate int 
)

insert into Categories(nume) values
('jucarii'),('dulciuri'),('fructe')

insert into Products(nume, descriere, cid) values 
('Ursulet plus','plusel plus plus', 1),
('Papusa Barbie', 'papusa top-model', 1),
('Ciocolata', 'Ciocolata de la Lidl, cea mai buna ciocolata',2),
('Jeleuri', 'Tot de la Lidl', 2),
('Portocale','Craciunul inseamna miros de portocale',3)

select * from Categories
select nume from Products

BEGIN TRAN
WAITFOR DELAY '00:00:04'
INSERT INTO Categories(nume) VALUES ('incaltaminte') 
COMMIT TRAN

create index idx_products_nume on Products(nume) 

