create or alter view ViewParticipanti
as 
	select Nume,Data_n from Participanti
go

create or alter view ViewEvenimenteGrupateData
as 
	select Data_ev, count(*) as NrEvenimente from Evenimente group by Data_ev
go

if exists (select name from sys.indexes where name='N_idx_Participanti_Nume_DataN')
	drop index N_idx_Participanti_Nume_DataN on Participanti
go
create nonclustered index N_idx_Participanti_Nume_DataN on Participanti(Nume,Data_n)
go

if exists (select name from sys.indexes where name='N_idx_Evenimente_Detalii')
	drop index N_idx_Evenimente_Detalii on Evenimente
go
create nonclustered index N_idx_Evenimente_Detalii on Evenimente(Denumire, Data_ev)
go	

select Nume from ViewParticipanti order by Nume

select * from ViewEvenimenteGrupateData order by NrEvenimente

