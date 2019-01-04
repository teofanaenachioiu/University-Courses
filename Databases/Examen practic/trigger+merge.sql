-- trigger

create table Arhiva(
	nume varchar(50),
	prenume varchar(50),
	oras varchar(50)
);
go

create trigger [La stergere persoane]
ON Persoane
for delete
as
begin
	set nocount on;
	insert into Arhiva(nume, prenume,oras)
	select nume,prenume, oras from deleted;
end;
go

select * from Persoane;
go

update Persoane set nume='Apopei' where prenume='Ioana'

delete Persoane where nume='Avram'
go

select * from Arhiva

insert into Persoane(nume,prenume,oras)
values('Plescan','Maria',null);



--merge

merge Persoane
using (select max(nume) as 'nume',prenume,oras from Persoane group by prenume,oras)
MergePersoane on Persoane.nume=MergePersoane.nume
when matched then
update set Persoane.prenume=MergePersoane.prenume,
Persoane.oras=MergePersoane.oras
when not matched by source then delete;