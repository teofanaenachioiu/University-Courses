create database TabaraDeVara
go
use TabaraDeVara
go

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

create table Moderatori(
	Mid int primary key,
	Nume varchar(50) not null,
	Telefon varchar(10) not null,
	Data_n date
)

create table Grupe(
	Gid int primary key,
	Denumire varchar(40) not null  
	constraint FK_Grupe_Insotitori foreign key(Gid) references Moderatori(Mid)
)

create table Participanti(
	Pid int primary key,
	Aid int foreign key references Asociatii(Aid),
	Gid int foreign key references Grupe(Gid),
	Nume varchar(50) not null,
	Data_n date not null, 
	constraint CK_Data check (Data_n>'1997-01-01')
)

create table Cazari(
	Cid int primary key,
	Locatie varchar(20) not null,
	NrLocuri int 
)

create table Cereri(
	Aid int foreign key references Asociatii(Aid),
	Cid int foreign key references Cazari(Cid),
	CONSTRAINT PK_Cereri primary key(Aid, Cid)
)

create table Evenimente(
	Eid int primary key,
	Denumire varchar(50) not null,
	Data_ev date not null,
	Ora time
)

create table Inscrieri(
	Eid int foreign key references Evenimente(Eid),
	Gid int foreign key references Grupe(Gid),
	CONSTRAINT PK_Inscrieri primary key(Eid, Gid)
)

create table Voluntari (
	Vid int primary key,
	Nume varchar(50) not null,
	Data_n date
)

create table Contracte(
	Eid int foreign key references Evenimente(Eid),
	Vid int foreign key references Voluntari(Vid),
	CONSTRAINT PK_Contracte primary key(Eid, Vid)
)

