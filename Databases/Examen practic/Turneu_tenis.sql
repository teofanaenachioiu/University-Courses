-- 1) Modelul de date relational corespunzator reprezentarii datelor

create database Turneu_tenis

use Turneu_tenis
go

create table Turnee(
tid int identity(1,1) primary key,
locatie varchar(30),
start_ev date,
end_ev date
)

create table Arene(
aid int primary key,
)

create table Jucatori(
jid int identity(1,1) primary key,
nume varchar (30),
puncte int,
valoare int
)

create table Partide(
pid int identity(1,1) primary key,
aid int foreign key references Arene(aid),
tid int foreign key references Turnee(tid),
data_ev datetime
)

create table Games(
pid int foreign key references Partide(pid),
jid1 int foreign key references Jucatori(jid),
jid2 int foreign key references Jucatori(jid),
puncte1 int,
puncte2 int,
valoare1 int,
valoare2 int,
jidc int,
constraint PK_Games primary key(pid,jid1,jid2)
)



insert into Arene(aid)
values
(1),
(2)

insert into Jucatori(nume, puncte, valoare)
values
('Simona Halep',6100,12),
('Serena Williams',5750,10),
('Angelique Kerber',5500,11),
('Caroline Wozniacki',5000,10),
('Karolina Pliskova',4900,9),
('Naomi Osaka',4850,9),
('Jelena Ostapenko',4500,8)

insert into Turnee(locatie,start_ev,end_ev)
values
('Australia','2019-01-07','2019-01-12'),
('Franta','2019-05-26','2019-06-08'),
('Marea Britanie','2019-07-01','2019-07-13')

insert into Partide(aid,tid,data_ev)
values
(1,1,'2019-01-07 20:30:00'),
(1,1,'2019-01-08 20:00:00'),
(2,1,'2019-01-10 15:30:30'),
(1,2,'2019-05-26 20:00:00'),
(2,3,'2019-07-01 20:00:00')

insert into Games(pid,jid1,jid2,puncte1,puncte2,valoare1,valoare2,jidc)
values
(1,1,2,100,20,1,0,1),
(2,3,4,150,50,1,0,3),
(3,1,3,200,100,2,1,1)

select * from Turnee
select * from Arene
select * from Jucatori
select * from Partide
select * from Games

drop table Games
drop table Partide
drop table Jucatori
drop table Arene
drop table Turnee

go


-- 2) Creati o procedura stocata ce primeste un turneu, doi jucatori, 
-- valorile punctelor si premiilr obtinute de acestea, jucatorul castigator,
-- arena, data si ora desfasurarii si adauga partida in baza de date.

create or alter procedure add_partida(
	@tid int, @jid1 int, @jid2 int, @puncte1 int, @puncte2 int, @valoare1 int, @valoare2 int, @jidc int, @aid int, @data_ora_ev datetime
)
as
begin 
	if(@jidc <> @jid1 and @jidc<>@jid2)
	print 'Jucatorul castigator nu corespunde niciunui jucator dat'
	else 
		begin
			declare @nr int=0;
			select @nr=count(*) 
				from Games G 
				where (@jid1=G.jid1 or @jid1=G.jid2) and (@jid2=G.jid1 or @jid2=G.jid2)
			if(@nr<>0)
			print 'Jucatorii au jucat deja un meci'
			else 
				begin
					insert into Partide(aid,tid,data_ev)
					values (@aid, @tid,@data_ora_ev)
					declare @pid int;
					select top 1 @pid = pid from Partide order by pid desc
					insert into Games(pid,jid1,jid2,puncte1,puncte2,valoare1,valoare2,jidc)
					values
					(@pid,@jid1,@jid2,@puncte1,@puncte2,@valoare1,@valoare2,@jidc)
					print 'S-a adaugat partida'
				end
		end
end;

exec add_partida 1,1,4,100,50,2,1,4,1,'2019-01-10 20:00:00'

go


-- 3) Creati un view care returneaza lista jucatorilor si numarul partidelor
-- castigate descrescator.

create or alter view vw_Jucatori_Partide
as
select J.nume as 'Nume jucator', count(*) as 'Numar partide castigate'
from Jucatori J
inner join Games G on G.jid1=J.jid or G.jid2=J.jid
where G.jidc=J.jid
group by J.nume;

select * from vw_Jucatori_Partide order by [Numar partide castigate] desc

go


-- 4) Creati o functie care pentru un anumit jucator va returna totalul 
-- punctelor si premiile castigate pe durata campiontului cumulat cu valorile
-- deja avute la inceputul campionatului.

create or alter function uf_punctaj_jucator
(@jid int)
returns @Tabel table (nume nvarchar(30), puncte int, valoare int)
as 
begin
	declare @puncte1 int=0;
	declare @puncte2 int=0;
	declare @valoare1 int=0;
	declare @valoare2 int=0;

	select @puncte1=sum(G.puncte1), @valoare1=sum(G.valoare1) from Games G where G.jid1=@jid
	select @puncte2=sum(G.puncte2),@valoare2=sum(G.valoare2) from Games G where G.jid2=@jid

	if(@puncte1 is NULL) set @puncte1=0;
	if(@puncte2 is NULL) set @puncte2=0;
	if(@valoare1 is NULL) set @valoare1=0;
	if(@valoare2 is NULL) set @valoare2=0;

	insert @Tabel(nume,puncte,valoare)
	select J.nume, J.puncte + @puncte1 + @puncte2 as 'Puncte', J.valoare + @valoare1 + @valoare2 as 'Valoare' 
	from Jucatori J 
	where J.jid=@jid
	group by J.nume, J.puncte, J.valoare
	return
end


select * from uf_punctaj_jucator(1)