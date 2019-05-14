CREATE OR ALTER PROCEDURE AddGrupaEvenimentNoRoll @denumireGr varchar(40), @denumireEv varchar(50), @dataEv date, @oraEv time(7) as
begin 

begin tran
	begin try

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
		commit tran
		select 'Transaction committed for Grupe'
	end try

	begin catch
		rollback tran
		select 'Transaction rollbacked for Grupe'
	end catch

begin tran
	begin try
		if dbo.validare_Denumire(@denumireEv) <>1
		begin 
			print 'denumire event'
			raiserror('Data invalida',14,1);
		end 

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

		declare @eid int;
		select top 1 @eid = Eid from Evenimente order by Eid desc
		set @eid = @eid +1
		print @eid
		insert into Evenimente(Eid, Denumire, Data_ev, Ora) values (@eid, @denumireEv, @dataEv, @oraEv)
		commit tran
		select 'Transaction committed for Evenimente'
	end try

	begin catch
		rollback tran
		select 'Transaction rollbacked for Evenimente'
	end catch

begin tran
	begin try	
		insert into Inscrieri(Eid, Gid) values (@eid, @gid)
		commit tran
		select 'Transaction committed for Inscrieri'
	end try

	begin catch
		rollback tran
		select 'Transaction rollbacked for Inscrieri'
	end catch

end

-- Testarea --

select * from Grupe 
select * from Evenimente
select * from Inscrieri

exec AddGrupaEvenimentNoRoll 'GrupaNouaSGBD_2', 'EventNouSGBD_2', '2019-05-13', '15:30:00'
exec AddGrupaEvenimentNoRoll 'GrupaNouaSGBD_2', '' , '2019-05-13', '15:30:00'

select * from Grupe 
select * from Evenimente
select * from Inscrieri
