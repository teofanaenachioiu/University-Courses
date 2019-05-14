CREATE OR ALTER PROCEDURE AddGrupaEveniment @denumireGr varchar(40), @denumireEv varchar(50), @dataEv date, @oraEv time(7) as
begin 
begin tran

begin try
	if dbo.validare_DataCalendaristica(@dataEv) <> 1
		begin 
			print 'data'
			raiserror('Data invalida',14,1);
		end 
	if dbo.validare_Ora(@oraEv) = 1
		begin 
			print 'ora'
			raiserror('Ora invalida',14,1);
		end
	if dbo.validare_Denumire(@denumireEv) <>1
		begin 
			print 'denumire event'
			raiserror('Data invalida',14,1);
		end 
	if dbo.validare_Denumire(@denumireGr) <>1
		begin 
			print 'denumire grupa'
			raiserror('Data invalida',14,1);
		end 

	declare @gid int;
	select top 1 @gid = Gid from Grupe order by Gid desc
	set @gid = @gid +1
	print @gid
	print @denumireGr
	INSERT INTO Moderatori VALUES(@gid,'data_de_test','0000000000',null);
	insert into Grupe(Gid, Denumire) values (@gid, @denumireGr)

	print 'aici'

	declare @eid int;
	select top 1 @eid = Eid from Evenimente order by Eid desc
	set @eid = @eid +1
	print @eid
	insert into Evenimente(Eid, Denumire, Data_ev, Ora) values (@eid, @denumireEv, @dataEv, @oraEv)

	insert into Inscrieri(Eid, Gid) values (@eid, @gid)

	commit tran
	select 'Transaction committed'
end try

begin catch
	rollback tran
	select 'Transaction rollbacked'
end catch

end

create or alter function dbo.validare_Denumire (@denumire varchar(50))
returns bit
as
begin 
	declare @flag bit
	set @flag =1

	if @denumire is null or @denumire =''
		set @flag=0;
	return @flag;
end
go


-- Testarea --

select * from Grupe 
select * from Evenimente
select * from Inscrieri

exec AddGrupaEveniment 'GrupaNouaSGBD_1', 'EventNouSGBD_1', '2019-05-13', '15:30:00'
exec AddGrupaEveniment 'GrupaNouaSGBD_1', '' , '2019-05-13', '15:30:00'

select * from Grupe 
select * from Evenimente
select * from Inscrieri

