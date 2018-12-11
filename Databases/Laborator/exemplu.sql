create table Ceaiuri(
	cid int primary key identity,
	denumire varchar(50),
	pret int)

insert into Ceaiuri values
('Menta',10),
('Ghimbir',15),
('Fructie',12),
('Tei',9)

select * from Ceaiuri order by cid



select * from sys.indexes

if exists (select name from sys.indexes where name='N_idx_Ceaiuri_Denumire')
	drop index N_idx_Ceaiuri_Denumire ON Ceaiuri
create nonclustered index N_idx_Ceaiuri_Denumire on Ceaiuri(Denumire)

select * from Ceaiuri order by denumire

select * from Ceaiuri where denumire like 'A%'