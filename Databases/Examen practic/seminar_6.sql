-- 1) Write an SQL script that creates the corresponding relational data model.

create database Train_scheduling_management

use Train_scheduling_management
go

create table TRAINS_TYPE
(
	ttid int identity primary key,
	descriere varchar(50) 
)

create table TRAINS
(
	tid int identity primary key,
	nume varchar(30),
	tip int foreign key references TRAINS_TYPE(ttid)
)

create table STATIONS 
(
	stid int identity primary key,
	nume varchar(30)
)

create table ROUTES
(
	rid int identity primary key,
	nume varchar(30),
	tid int foreign key references TRAINS(tid)
)

create table STATIONS_ROUTES
(
	stid int foreign key references STATIONS(stid),
	rid int foreign key references ROUTES(rid),
	sosire time,
	plecare time,
	constraint pk_stations_routes PRIMARY KEY (stid, rid)
)



select * from TRAINS_TYPE
select * from TRAINS
select * from STATIONS
select * from ROUTES
select * from STATIONS_ROUTES

drop table TRAINS
drop table TRAINS_TYPE
drop table STATIONS_ROUTES
drop table STATIONS
drop table ROUTES

INSERT INTO TRAINS_TYPE VALUES('description 1'), ('descrition 2')
INSERT INTO TRAINS values ('InterRegio', 1), ('Intercity', 1), ('Regio', 2)
INSERT INTO STATIONS values ('Cluj-Napoca'), ('Brasov'), ('Bucuresti')
Insert into ROUTES values ('Sighisoara', 1), ('Medias', 2)
INSERT STATIONS_ROUTES VALUES(1,1,'12:00:00', '18:00:00'), (1,2,'15:30:00', '22:42:00'),
(2,2,'08:05:00', '21:48:00')
GO

-- 2) Create a stored procedure that receives a route, a station, arrival and departure
--times and adds the station to the route. (1p)

create or alter procedure add_Stops

	@stid int, @rid int, @start time, @end time

as 
begin 
	declare @apare int;

	select @apare=count(*) from STATIONS_ROUTES where stid=@stid and rid=@rid;
	if(@apare=0)
	insert into STATIONS_ROUTES(stid,rid,sosire,plecare) values(@stid,@rid,@start,@end);
	else print 'Entitatea exista deja'	
end

select * from STATIONS_ROUTES
EXEC Add_Stops 2,1, '5:00:00', '9:00:00'
select * from STATIONS_ROUTES

-- 3) Create a view that shows the names of the routes that contain all the stations.(2p)
go

create or alter view vw_routes
as 
select nume from ROUTES
where rid = 
	(
		select R.rid
		from ROUTES R
		inner join STATIONS_ROUTES ST on R.rid=ST.rid	
		group by R.rid
		having count(ST.stid) = (SELECT COUNT(*) from STATIONS_ROUTES)
	)

select * from vw_routes

-- 4) Create a function that lists the names of the stations with more than R routes,
--where R>=1 is a function parameter. (2p)

create or alter function fct_routes(@R int)
returns table
as
return select distinct S.nume, COUNT(*) as 'nr of stations' FROM STATIONS S 
		inner join STATIONS_ROUTES ST ON ST.stid=S.stid
		group by S.stid, S.nume
		having count(*)>=@R

		SELECT * FROM fct_routes(1)
SELECT * FROM fct_routes(2)
SELECT * FROM fct_routes(3)

-- 4) Create a function that lists the names of the stations with more than one train
--at some point in the day (2p)

create or alter function fct_routes_time()
returns table
as
return select distinct S.nume as 'nr of trains' FROM STATIONS S 
		inner join STATIONS_ROUTES ST1 ON ST1.stid=S.stid
		inner join STATIONS_ROUTES ST2 ON ST2.stid=ST1.stid
		where (ST1.sosire >= ST2.plecare and  ST1.sosire <=ST2. plecare) 
		or(ST1.plecare >=ST2. sosire and ST1.plecare <=ST2.plecare)

SELECT * FROM fct_routes_time()