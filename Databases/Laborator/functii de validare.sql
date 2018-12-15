use TabaraDeVara
go

SET LANGUAGE us_english;  
SET DATEFORMAT ymd;  
go
-- Validare numar randuri
create or alter function dbo.validare_NrRanduri(@rows int)
returns bit
as
begin
	declare @flag bit;
	set @flag=0;
	if @rows>=0 
		set @flag=1;
	return @flag;
end
go


-- Validare id Asociatie
create or alter function dbo.validare_IdAsociatie (@aid int)
returns bit
as
begin 
	declare @start int;
	declare @end int;
	declare @flag bit;
	set @flag=0;

	select top 1 @start = Aid from Asociatii order by Aid asc;
	select top 1 @end = Aid from Asociatii order by Aid desc;
	if @aid between @start and @end 
		set @flag=1;
	return @flag;
end
go

-- Validare id Grupa
create or alter function dbo.validare_IdGrupa (@gid int)
returns bit
as
begin 
	declare @start int;
	declare @end int;
	declare @flag bit;
	set @flag=0;

	select top 1 @start = Gid from Grupe order by Gid asc;
	select top 1 @end = Gid from Grupe order by Gid desc;
	if @gid between @start and @end 
		set @flag=1;
	return @flag;
end
go

-- Validare id Evenimente
create or alter function dbo.validare_IdEvenimente (@eid int)
returns bit
as
begin 
	declare @start int;
	declare @end int;
	declare @flag bit;
	set @flag=0;

	select top 1 @start = Eid from Evenimente order by Eid asc;
	select top 1 @end = Eid from Evenimente order by Eid desc;
	if @eid between @start and @end 
		set @flag=1;
	return @flag;
end
go

-- Validare format DATE
create or alter function dbo.validare_DataCalendaristica (@data varchar(15))
returns bit
as
begin 
	return ISDATE(@data);
end
go

-- Validare format ORA
create or alter function dbo.validare_Ora (@data varchar(15))
returns bit
as
begin 
	return ISDATE(@data);
end
go

-- Validare data nasterii Participanti
create or alter function dbo.validare_DataN (@data date)
returns bit
as
begin 
	declare @start date;
	declare @end date;
	declare @flag bit;
	set @flag=0;
	set @start='1997-01-01'
	--set @end='2008-12-30'

	if @data >=@start
		set @flag=1;
	return @flag;
end
go
