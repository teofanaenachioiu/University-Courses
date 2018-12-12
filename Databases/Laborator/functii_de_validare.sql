use TabaraDeVara
go

-- Functii de validare

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