-- 1) Modelul relational corespunzator pentru a reprezenta datele

create database Alfabete_db
use Alfabete_db
go

create table Limbi(
lid int identity(1,1) primary key,
denumire varchar(30) not null
)

insert into Limbi(denumire)
values
('spaniola'),
('romana'),
('rusa')

create table Persoane(
pid int identity(1,1) primary key,
nume varchar(30) not null,
varsta int not null,
lid int foreign key references Limbi
)

insert into Persoane(nume, varsta, lid)
values
('Carla',20,1),
('Maria',21,2),
('Igor',25,3),
('Vanessa',18,1),
('Horia',27,2)

create table Alfabete(
aid int identity(1,1) primary key,
denumire varchar(30) not null,
vechime int
)

insert into Alfabete(denumire,vechime)
values
('latin',12),
('slavic',7)

create table Comunitati(
cid int identity(1,1) primary key,
nume varchar(30) not null,
nr_pers int,
aid int foreign key references Alfabete(aid)
)

insert into Comunitati(nume,nr_pers,aid)
values
('spanioli',35,1),
('romani',18,1),
('rusi',100,2)

create table Cunostinte(
aid int foreign key references Alfabete(aid),
pid int foreign key references Persoane(pid),
nr_pers int,
constraint PK_Cunos primary key (aid, pid)
)

insert into Cunostinte(aid, pid,nr_pers)
values
(1,1,20),
(1,2,26),
(1,3,20),
(2,1,25),
(2,4,50),
(2,5,50)

select * from Limbi
select * from Persoane
select * from Alfabete
select * from Comunitati
select * from Cunostinte
go


-- 2) Creati o procedura stocata care primeste un alfabet si 
-- o presoana, un numar de presoane si adauga alfabetul persoanei. 
-- Daca exista deja, se actualizeaza numarul de persoane.

create or alter procedure proc_add_cunostinta(
@aid int, @pid int, @nr_pers int
)
as
begin 
	declare @nr int=0;
	select @nr=count(*) from Cunostinte where aid=@aid and pid=@pid
	if(@nr<>0) 
		begin
			update Cunostinte set nr_pers=@nr_pers
			where aid=@aid and pid=@pid
			print 'S-a actualizat numarul de persoane!' 
		end
	else 
		begin
			insert into Cunostinte(aid,pid, nr_pers)
			values(@aid,@pid,@nr_pers)
			print 'S-a adaugat alfabetul!'
		end
end;

select * from Cunostinte
exec proc_add_cunostinta 2,1,100
go


-- 4) Creati o functie care afiseaza nr mediu de persoane ce 
-- folosesc un alfabet

create or alter function uf_numarMediuPers()
returns table
as
return 
select A.denumire, avg(C.nr_pers) as 'Medie persoane'
from Alfabete A
inner join Cunostinte C on C.aid=A.aid
group by A.denumire

select * from uf_numarMediuPers()
go


-- 3) Creati un view care afiseaza corespunzator unui alfabet 
-- comunitatile ce il folosesc

create or alter view vw_alfabet_comunitate
as
select A.denumire, C.nume
from Alfabete A
inner join Comunitati C on C.aid=A.aid

select * from vw_alfabet_comunitate