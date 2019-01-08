-- 1) Modelul de date relational corespunzator reprezentarii datelor necesare

create database Inchirieri_auto

use Inchirieri_auto
go

create table Marci(
mid int identity(1,1) primary key,
denumire varchar(30) 
)

insert into Marci(denumire)
values
('Dacia'),
('Citroen'),
('VW')

create table Clienti(
cid int identity(1,1) primary key,
nume varchar(30),
prenume varchar(30)
)

insert into Clienti(nume, prenume)
values 
('Popa','Ionut'),
('Rusu','Maria'),
('Cosma','Alexandru')

create table Angajati(
aid int identity(1,1) primary key,
nume varchar(30),
prenume varchar(30)
)

insert into Angajati(nume, prenume)
values 
('Nistor','Vasile'),
('Simion','Andrei'),
('Moldovan','Claudiu')

create table Autovehicule(
numar varchar(7) primary key,
motorizare varchar(30),
mid int foreign key references Marci(mid)
)

insert into Autovehicule(numar,motorizare,mid)
values
('SV10ABC','disel',1),
('SV12FHL','benzina',2),
('CJ10IOI','motorina',3),
('B100JOY','gaz',1)


create table Inchirieri(
id int identity(1,1) primary key,
aid int foreign key references Angajati (aid),
numar varchar(7) foreign key references Autovehicule(numar),
cid int foreign key references Clienti(cid),
d_inchiriere datetime,
d_returnare datetime
)

insert into Inchirieri(aid,numar,cid,d_inchiriere,d_returnare)
values
(1,'SV10ABC',1,'2019-01-03 10:00:00','2019-01-03 20:00:00'),
(2,'B100JOY',2,'2019-01-03 10:00:00','2019-01-05 20:00:00'),
(3,'CJ10IOI',3,'2019-01-06 10:00:00','2019-01-07 10:00:00')

select * from Marci
select * from Clienti
select * from Angajati
select * from Autovehicule
select * from Inchirieri
go


-- 2) Creati o procedura stocata care primeste un angajat, un autovehicul, un
-- client, data si ora inchirierii si data si ora returnarii si un parametru 
-- tip operatie de tip bit. Daca tip operatie este true atunci se insereaza un
-- rand in registrul de inchirieri, altfel se actualizeaza data si ora inchirierii, 
-- respectiv data si ora returnarii.

create or alter procedure proc_inchiriere(
@aid int, @numar varchar(7),@cid int, @d_inchiriere datetime, @d_returnare datetime, @tip bit
)
as
begin 
	if(@tip =1)
		begin
			insert into Inchirieri(aid,numar,cid,d_inchiriere,d_returnare)
			values(@aid,@numar,@cid,@d_inchiriere,@d_returnare)
			print 'added!'
		end
	else
		begin 
			update Inchirieri set d_inchiriere=@d_inchiriere, d_returnare=@d_returnare
			where aid=@aid and @numar=numar and cid=@cid
			print 'updated!' 
		end
end;

exec proc_inchiriere 1,'SV12FHL',3,'2019-01-03 10:00:00','2019-01-05 20:00:00',1
go


-- 3) Creati un view ce afiseaza o lista a angajatilor care au inchiriat in luna 
-- curenta cel putin un autovehicul ce apartine unei marci date (ex: Dacia). 
-- Angajatii vor fi ordonati alfabetic si va fi afisat numarul total de inchirieri
-- din luna curenta pentru fiecare angajat

create or alter view vw_Angajati
as
select A.nume +' '+A.prenume as 'Nume', count(I.id) as 'Nr inchirieri', M.denumire as 'Marca'
from Angajati A
inner join Inchirieri I on I.aid=A.aid
inner join Autovehicule Au on Au.numar=I.numar
inner join Marci M on M.mid=Au.mid
where month(I.d_inchiriere)=MONTH(GETDATE())
group by A.nume, A.prenume, M.denumire
having count(I.id)>=1

select Nume, [Nr inchirieri] from vw_Angajati
where Marca='Dacia'
go


-- 4) Creati o functie care returneaza o lista a autovehiculelor (numar, marca, tip
-- motorizare) libere (neinchiriate) pentru o anumita data si ora. Coloanele Listei 
-- sunt: Numar autovechicul, Marca si Tip Motorizare

create or alter function uf_autovehicule(
@moment datetime
)
returns table
as
return 
select A.numar, M.denumire, A.motorizare,I.id
from Autovehicule A
inner join Marci M on A.mid=M.mid
full join Inchirieri I on I.numar=A.numar
where @moment not between I.d_inchiriere and I.d_returnare

select * from uf_autovehicule('2019-01-03 10:00:00')