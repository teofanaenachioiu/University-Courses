create database TabaraDeVara

drop table Insotitori
drop table Asociatii
drop table Grupe
drop table Participanti

create table Insotitori(
	Iid int primary key,
	Nume varchar(30) not null,
	Telefon varchar(10) default 'Set phone number'
)

create table Asociatii(
	Aid int primary key,
	Denumire varchar(30) not null
	constraint FK_Asociatii_Insotitori foreign key(Aid) references Insotitori(Iid)
)

create table Grupe(
	Gid int primary key,
	Denumire varchar(40) not null  
)

create table Participanti(
	Pid int primary key,
	Aid int foreign key references Asociatii(Aid),
	Gid int foreign key references Grupe(Gid),
	Nume varchar(50) not null,
	Data_n date not null
)
