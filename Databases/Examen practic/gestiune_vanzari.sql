-- 1) Modelul de date relational corespunzator reprezentarii datelor

create database Gestiune_vanzari
use Gestiune_vanzari
go

create table Clienti(
cid int identity(1,1) primary key,
denumite varchar(30),
cod_fiscal varchar(30)
)

create table Agenti(
aid int identity(1,1) primary key,
nume varchar(30),
prenume varchar(30)
)

create table Produse(
pid int identity(1,1) primary key,
denumire varchar(30),
unitate varchar(30)
)

create table Facturi(
numar int identity(1,1) primary key,
enitere date,
cid int foreign key references Clienti(cid),
aid int foreign key references Agenti(aid),
)

create table Vanzari(
vid int identity primary key,
pret int,
cantitate int,
numar int foreign key references Facturi(numar),
pid int foreign key references Produse(pid)
)
go

insert into Clienti(denumite,cod_fiscal) 
values
('SC Forever SRL','12345'),
('SC Amway SRL','23456')

insert into Agenti(nume,prenume)
values
('Popa','Vlad'),
('Nistor','Maria') 

insert into Produse(denumire,unitate)
values
('Crema hidratanta','ml'),
('Blush','g')

insert into Facturi(enitere,cid,aid)
values
('2019-01-02',1,1),
('2018-12-03',1,2),
('2018-11-20',1,2),
('2018-11-21',2,1),
('2018-09-19',2,2)

insert into Vanzari (pret,cantitate,numar,pid)
values
(55,2,1,1),
(40,3,2,2),
(20,5,3,1),
(60,10,4,2),
(25,3,5,2)


select * from Clienti
select * from Agenti
select * from Produse
select * from Facturi
select * from Vanzari
go
-- 2) Creati o procedura stocata care primeste o factura, un numar de produs,
-- numarul de ordine, pretul si cantitatea si adauga noul produs facturii.
-- Daca produsul exista deja se adauga un rand nou cu cantitatea negativa 
-- (se face stonare).

create or alter procedure add_produs 
(@numar int, @pid int, @vid int, @pret int, @cantitate int)
as
begin
	declare @num int =0;
	select @num=count(*) from Vanzari V where @vid=V.vid;
	if(@num=0)
		insert into Vanzari(pret,cantitate,numar,pid) 
		values (@pret,@cantitate,@numar,@pid);
	else 
		update Vanzari set cantitate=@cantitate*(-1) where vid=@vid;
end

exec add_produs 1,1,1,31,5

select * from Vanzari
go

-- 3) Creati un view care afiseaza lista facturilor ce contin produsul Blush
-- si a caror valoare este mai mare de 300 de lei. Lista va afisa, pentru fiecare
-- factura, denumirea clientului, numarul facturii, data emiterii si valoarea 
-- toatala a facturii

create or alter view vw_facturi
as
select C.denumite,F.numar,F.enitere,V.cantitate*V.pret as 'valoare totala' 
from Facturi F
inner join Clienti C on F.cid=C.cid
inner join Vanzari V on V.numar=F.numar
inner join Produse P on P.pid=V.pid
where P.denumire='Blush' and V.cantitate*V.pret>=300

go

select * from vw_facturi
go

-- 4) Creati o functie care afiseaza valoarea toatala a facturilor, grupate pe
-- lunile anului si pe agentii de vanzare prentru un anumit an, ordonate crescator
-- in ordinea lunilor si a numelor agentilor. Coloanele Listei sunt: Luna, NumeAgent,
-- PrenumeAgent, ValoareTotala.

create or alter function uf_facturi()
returns @Lista table (Luna int, NumeAgent varchar(30), PrenumeAgent varchar(30), ValoareTotala int)
as  
begin
	insert into @Lista 
		select Month(F.enitere) as 'Luna', A.nume as 'NumeAgent', A.prenume as 'PrenumeAgent', 
			sum(V.cantitate*V.pret) as 'ValoareTotala'
			from Facturi F
			inner join Agenti A on A.aid=F.aid
			inner join Vanzari V on V.numar=F.numar
			group by Month(F.enitere), A.nume,A.prenume
return;
end;
go

select * from  uf_facturi()
go

create or alter function uf_facturi()
returns table
as  
	return select Month(F.enitere) as 'Luna', A.nume as 'NumeAgent', A.prenume as 'PrenumeAgent', 
	sum(V.cantitate*V.pret) as 'ValoareTotala'
	from Facturi F
	inner join Agenti A on A.aid=F.aid
	inner join Vanzari V on V.numar=F.numar
	group by Month(F.enitere), A.nume,A.prenume
go