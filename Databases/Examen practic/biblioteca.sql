-- 1) Modelul de date relational corespunzator reprezentarii datelor necesare.

create database Biblioteca

use Biblioteca
go

create table Librarii
(lid int identity(1,1) primary key,
nume nvarchar(30),
adresa nvarchar(30)
)

create table Domenii
(did int identity(1,1) primary key,
descriere nvarchar(30)
)

create table Carti
(cid int identity(1,1) primary key,
titlu nvarchar(30),
data_buy date,
lid int foreign key references Librarii(lid)
)

create table Autori
(aid int identity(1,1) primary key,
nume nvarchar(30),
prenume nvarchar(30),
cid int foreign key references Carti(cid)
)


insert into Librarii(nume,adresa) values
('Alexandria','Falticeni'),
('Humanitas','Suceava'),
('Carturesti','Suceava')

insert into Domenii(descriere) values 
('literatura'),
('stiintific')

insert into Carti(titlu, data_buy, lid) values
('Povestea Faridei','2018-06-12',1),
('Alchimistul','2018-10-10',2),
('Biblia pierduta','2018-11-29',3),
('Padurea norvegiana','2018-03-10',1),
('Portretul lui Dorian Gray','2017-12-10',2),
('Zuleiha deschide ochii','2018-11-29',3),
('M-am hotarat sa devin prost','2018-11-29',1)

select * from Librarii
select * from Domenii
select * from Carti
select * from Autori
go

-- 2) Creati o procedura stocata care primeste un nume si prenume de autor si o 
-- carte si asociaza autorul la carte. Daca autorul nu exista il adauga mai intai
-- la autori. Daca atat autorul cat si cartea sunt deja asociate afiseaza un mesaj 
-- in care se precizeaza ca exista cartea si autorul.

create or alter procedure add_autor
(@nume nvarchar(30),@prenume nvarchar(30), @cid int)
as
begin
	declare @num int=0; 
	select @num=count(*) from Autori where cid=@cid and nume=@nume and prenume=@prenume
	if(@num=0)
		insert into Autori(nume,prenume,cid) values(@nume,@prenume,@cid)
	else print 'Exista cartea si autorul'
end

exec add_autor 'Coelho','Paulo',2
go

-- 3) Creati un view care afiseaza numarul cartilor cumparate dupa anul 2010 din 
-- fiecare librarie. Se exclud librariile in care numarul cartilor cumparate a fost
-- mai mic de 5. Datele se vor afisa in ordine invers alfabetica a numelui librariei.

create or alter view vw_carti_cumparate
as 
select L.nume, COUNT(*) as 'numar carti'
from Carti C
inner join Librarii L on L.lid=C.lid
where Year(C.data_buy)>=2010
group by L.lid,L.nume
having  COUNT(*)>=3 

select * from vw_carti_cumparate order by nume
go

-- 4) Creati o functie care afiseaza o lista a cartilor ce au fost scrise de un numar
-- dat de autori, ordonate dupa titlul cartii. Coloanele sunt: Libraria, Adresa, Titlul,
-- NrAutori

create or alter function uf_carti
(@nr int)
returns table
as
return 
select L.nume, L.adresa, C.titlu, Count(*) as 'nr autori'
from Librarii L
inner join Carti C on C.lid=L.lid
inner join Autori A on A.cid=C.cid
group by L.nume,L.adresa,C.titlu
having Count(*)>=@nr

select * from uf_carti(1)