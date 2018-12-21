use TabaraDeVara
go
set ansi_nulls on 
go
set quoted_identifier on
go

create or alter procedure verificare_Participanti
	@flag bit output,
	@mesaj varchar(30) output,
	@aid int, -- id asociatie
	@gid int, -- id grupa
	@nume varchar(50),
	@data date,
	@rows int
as
begin
	declare @suma int;
	set @flag=0;
	set @mesaj='';
	set @suma=0;
	if dbo.validare_DataCalendaristica(@data) = 1
	begin
		if dbo.validare_IdAsociatie(@aid)=0 
			set @mesaj=@mesaj+'IDasociatie ';
		if dbo.validare_IdGrupa(@gid) = 0 
			set @mesaj=@mesaj+ 'IDgrupa ' ;
		if dbo.validare_DataN(@data) =0 
			set @mesaj=@mesaj+'data ' ;
		if dbo.validare_NrRanduri(@rows)=0
			set @mesaj=@mesaj+'nrRanduri ';
		set @suma= @suma+dbo.validare_IdAsociatie(@aid)+dbo.validare_IdGrupa(@gid) + dbo.validare_DataN(@data)+dbo.validare_NrRanduri(@rows);
	end
	else 
		set @mesaj='data nu e calentaristica!'
	if @suma=4 
		set @flag=1;
end
go

create or alter procedure verificare_Evenimente
	@flag bit output,
	@mesaj varchar(30) output,
	@data date,
	@ora time(7),
	@rows int
as
begin
	declare @suma int;
	set @flag=0;
	set @mesaj='';
	set @suma=0;
	if dbo.validare_DataCalendaristica(@data) = 1
		set @mesaj=@mesaj+'format data! '
	if dbo.validare_Ora(@ora)=1
		set @mesaj=@mesaj+'fromat ora! '
	if dbo.validare_NrRanduri(@rows)=0
			set @mesaj=@mesaj+'nrRanduri ';
	set @suma= @suma+dbo.validare_DataCalendaristica(@data)+dbo.validare_Ora(@ora)+dbo.validare_NrRanduri(@rows);
	
	if @suma=2 
		set @flag=1;
end
go

create or alter procedure verificare_Inscrieri
	@flag bit output,
	@mesaj varchar(30) output,
	@eid int,
	@gid int
as
begin
	declare @suma int;
	set @flag=0;
	set @mesaj='';
	set @suma=0;
	if dbo.validare_IdEvenimente(@eid) = 1
		set @mesaj=@mesaj+'idEvenimente '
	if dbo.validare_IdGrupa(@gid)=1
		set @mesaj=@mesaj+'idGrupa '

	set @suma= @suma+dbo.validare_IdEvenimente(@eid)+dbo.validare_IdGrupa(@gid);

	if @suma=2 
		set @flag=1;
end
go


create or alter procedure verificare_Asociatii
	@flag bit output,
	@mesaj varchar(30) output,
	@rows int
as
begin
	set @flag=1;
	if dbo.validare_NrRanduri(@rows)=0
		begin
			set @mesaj='nrRanduri ';
			set @flag=0;
		end
end
go

create or alter procedure verificare_Grupe
	@flag bit output,
	@mesaj varchar(30) output,
	@rows int
as
begin
	set @flag=1;
	if dbo.validare_NrRanduri(@rows)=0
		begin
			set @mesaj='nrRanduri ';
			set @flag=0;
		end
end
go

--------------- Operatii CRUD pe tabela ASOCIATII ----------------

create or alter procedure CRUD_Asociatii
	@denumire varchar(30),
	@rows int
as 
begin 
	set nocount on;

	declare @i int;
	declare @aid int;
	declare @flag bit;
	declare @mesaj varchar(30)

	set @aid=0;

	select top 1 @aid=Aid from Asociatii order by Aid desc
	set @i=1;

	exec verificare_Asociatii @flag output,@mesaj output,@rows

	if @flag=0 
		print 'Eroare la datele de intrare: '+@mesaj
	else 
		begin
			-- INSERT --------------------------------------------------------------
			WHILE @i<=@rows
			BEGIN
				SET @aid=@aid+1	
				INSERT INTO Insotitori VALUES (@aid,'data_de_test',null); -- adaug mai intai in tabela Insotiori
				INSERT INTO Asociatii VALUES (@aid,@denumire)
				SET @i=@i+1
				print 'Asociatia '+@denumire+' a fost adaugata!';
			END

			-- SELECT --------------------------------------------------------------
			SELECT * from Asociatii

			-- UPDATE --------------------------------------------------------------
			UPDATE Asociatii set Denumire='DeSters' WHERE Aid>5

			-- DELETE --------------------------------------------------------------
			DELETE Asociatii where Denumire='DeSters';
			DELETE Insotitori where Nume='data_de_test'; --sterg si din tabela Insotitori

			print 'S-au efectuat operatiile CRUD pe tabela Asociatii'
	end
end
go


--------------- Operatii CRUD pe tabela GRUPE --------------------

create or alter procedure CRUD_Grupe
	@denumire varchar(30),
	@rows int
as 
begin 
	set nocount on;

	declare @i int;
	declare @gid int;
	declare @flag bit;
	declare @mesaj varchar(30)
	
	set @gid=100;

	select top 1 @gid=Gid from Grupe order by Gid desc
	set @i=1;

	exec verificare_Grupe @flag output,@mesaj output,@rows

	if @flag=0 
		print 'Eroare la datele de intrare: '+@mesaj
	else 
		begin

			-- INSERT --------------------------------------------------------------
			WHILE @i<=@rows
			BEGIN
				SET @gid=@gid+1	
				INSERT INTO Moderatori VALUES(@gid,'data_de_test','0000000000',null);
				INSERT INTO Grupe VALUES (@gid,@denumire)
				SET @i=@i+1
				print 'Grupa '+@denumire+' a fost adaugata!';
			END

			-- SELECT --------------------------------------------------------------
			SELECT * from Grupe

			-- UPDATE --------------------------------------------------------------
			UPDATE Grupe set Denumire='DeSters' WHERE Gid>107

			-- DELETE --------------------------------------------------------------
			DELETE Grupe where Denumire='DeSters';
			DELETE Moderatori where Nume='data_de_test'

			print 'S-au efectuat operatiile CRUD pe tabela Grupe'
	end
end
go


--------------- Operatii CRUD pe tabela PARTICIPANTI --------------

create or alter procedure CRUD_Participanti
	@nume varchar(50),
	@data date,
	@aid int, -- id asociatie
	@gid int, -- id grupa
	@rows int
as 
begin 
	set nocount on;

	--verificare parametrii
	--se returneaza un flag: 0 succes, 1 eroare

	declare @flag bit;
	declare @mesaj varchar(30)
	declare @i int;
	declare @pid int;
	
	set @i=1;
	set @pid=1000;

	select top 1 @pid=Pid from Participanti order by Pid desc

	exec verificare_Participanti @flag output,@mesaj output, @aid,@gid,@nume,@data,@rows

	if @flag=0 
		print 'Eroare la datele de intrare: '+@mesaj
	else 
		begin

			-- INSERT --------------------------------------------------------------
			WHILE @i<=@rows
			BEGIN
				SET @pid=@pid+1	
				INSERT INTO Participanti VALUES (@pid,@aid,@gid,@nume,@data)
				SET @i=@i+1
				print 'Participantul cu numele '+@nume+' a fost adaugat!';
			END

			-- SELECT --------------------------------------------------------------
			SELECT * from Participanti

			-- UPDATE --------------------------------------------------------------
			UPDATE Participanti set Nume='DeSters' WHERE Pid>1035

			-- DELETE --------------------------------------------------------------
			DELETE Participanti where Nume='DeSters'

			print 'S-au efectuat operatiile CRUD pe tabela Participanti'

		end	
end
go

--------------- Operatii CRUD pe tabela EVENIMENTE ----------------

create or alter procedure CRUD_Evenimente
	@denumire varchar(50),
	@data date,
	@ora time(7),
	@rows int
as 
begin 
	set nocount on;

	--verificare parametrii
	--se returneaza un flag: 0 succes, 1 eroare

	declare @flag bit;
	declare @mesaj varchar(30)
	declare @i int;
	declare @eid int;
	
	set @i=1;
	set @eid=0;

	select top 1 @eid=Eid from Evenimente order by Eid desc

	exec verificare_Evenimente @flag output,@mesaj output, @data, @ora,@rows

	if @flag=0 
		print 'Eroare la datele de intrare: '+@mesaj
	else 
		begin

			-- INSERT --------------------------------------------------------------
			WHILE @i<=@rows
			BEGIN
				SET @eid=@eid+1	
				INSERT INTO Evenimente VALUES (@eid,@denumire,@data,@ora)
				SET @i=@i+1
				print 'Evenimentul cu denumirea '+@denumire+' a fost adaugat!';
			END

			-- SELECT --------------------------------------------------------------
			SELECT * from Evenimente

			-- UPDATE --------------------------------------------------------------
			UPDATE Evenimente set Denumire='DeSters' WHERE Eid>30

			-- DELETE --------------------------------------------------------------
			DELETE Evenimente where Denumire='DeSters'

			print 'S-au efectuat operatiile CRUD pe tabela Evenimente'

		end	
end
go

--------------- Operatii CRUD pe tabela INSCRIERI -----------------

create or alter procedure CRUD_Inscrieri
	@eid int,
	@gid int
as 
begin 
	set nocount on;

	--verificare parametrii
	--se returneaza un flag: 0 succes, 1 eroare

	declare @flag bit;
	declare @mesaj varchar(30);

	exec verificare_Inscrieri @flag output,@mesaj output, @eid, @gid

	if @flag=0 
		print 'Eroare la datele de intrare: '+@mesaj
	else 
		begin

			-- INSERT --------------------------------------------------------------
			INSERT INTO Inscrieri VALUES (@eid,@gid)
			print 'Inscrierea a fost efectuata!';

			-- SELECT --------------------------------------------------------------
			SELECT * from Inscrieri

			-- UPDATE --------------------------------------------------------------
			UPDATE Inscrieri set Gid=100 WHERE Gid>1000

			-- DELETE --------------------------------------------------------------
			DELETE Inscrieri WHERE Gid=@gid and Eid=@eid

			print 'S-au efectuat operatiile CRUD pe tabela Inscrieri'

		end	
end
go


create or alter procedure CRUD_main
as
begin 
	exec CRUD_Participanti 'Teofana','1998-04-11',1,101,1
	exec CRUD_Asociatii 'ATOR Cluj',1
	exec CRUD_Grupe 'GrupaNoua',10
	exec CRUD_Evenimente 'FaraDenumire','2018-07-21','15:30:00',1
	exec CRUD_Inscrieri 3, 102
end
go

exec CRUD_Participanti 'Teofana','1998-04-11',1,101,1
exec CRUD_Asociatii 'ATOR Cluj',1
exec CRUD_Grupe 'GrupaNoua',10
exec CRUD_Evenimente 'FaraDenumire','2018-07-21','15:30:00',1
exec CRUD_Inscrieri 3, 102
go 