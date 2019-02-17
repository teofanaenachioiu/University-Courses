create database EXAMEN
use EXAMEN
go
CREATE TABLE R(A integer, B integer, C integer);

/* Create few records in this table */
INSERT INTO R VALUES(1,2,3);
INSERT INTO R VALUES(1,2,4);
INSERT INTO R VALUES(3,3,5);


/* Display all the records from the table */

CREATE TABLE S(A integer, D integer);

INSERT INTO S VALUES(1,4);
INSERT INTO S VALUES(1,7);
INSERT INTO S VALUES(3,8);

SELECT DISTINCT R.B FROM R LEFT OUTER JOIN S ON R.B=S.A
SELECT DISTINCT S.A FROM R RIGHT OUTER JOIN S ON R.C=S.D

INSERT INTO R VALUES(10,3,3);
INSERT INTO S VALUES(11,3);

SELECT * FROM R INNER JOIN S ON R.A=S.A;
SELECT * FROM R LEFT JOIN S ON R.A=S.A;


select * from R
union 
select * from R


create table T(a integer, b integer)

insert into T(a,b)
values
(1,3),(1,1),(3,1),(null,4),(5,null)

select * from T

select a from T t1
where exists(select * from T where a=t1.b)

select a from T
where b= any (select a from T)

insert into R(a,b)
values (7,3)

create table R1(a integer, b integer, c integer)
create table R2(a integer, d integer)

insert into R1(a,b,c)
values 
(1,2,3),
(1,2,4),
(3,3,5)

insert into R2(a,d)
values
(1,4),
(1,7),
(3,8)

select R1.a from R1 inner join R2 on R1.a=R2.a except select R1.a from R1

select R1.a from R1 inner join R2 on R1.a=R2.a intersect select R1.a from R1


-- verificare constrangere a->bc
create table DF(a integer, b integer, c integer, d integer)

insert into DF(a,b,c,d)
values
(1,3,5,1),
(1,3,5,4),
(2,3,4,5)

select  * from DF d1, DF d2
where d1.a=d2.a and d1.b!=d2.b or d1.a=d2.a and d1.c!=d2.c

select * from DF

insert into DF(a,b,c,d)
values (1,3,4,4)

delete from DF

-- verificare ACD cheie candidat si dependenta functionala A->B

create table CheieCandidat(a integer, b integer, c integer, d integer)

insert into CheieCandidat
values
(1,2,3,4),
(2,3,4,5),
(3,3,4,5)
select * from CheieCandidat

-- daca e corect, da o tabela de dimensiunea tebelei initiale
select count(*) from CheieCandidat c1, CheieCandidat c2
where c1.a=c2.a and c1.c=c2.c and c1.d=c2.d -- tabela de dimensiunea tabelei
union all
select count(*) from CheieCandidat c1, CheieCandidat c2
where c1.a=c2.a and c1.b!=c2.b -- ar trebui sa dea 0 daca e corect
union all
select count(*) from CheieCandidat c1, CheieCandidat c2
where c1.a=c2.a and c1.c=c2.c -- ar trebui sa fie diferita de dimensiunea tabelei
union all
select count(*) from CheieCandidat c1, CheieCandidat c2
where c1.a=c2.a and c1.d=c2.d -- ar trebui sa fie diferita de dimensiunea tabelei
union all
select count(*) from CheieCandidat c1, CheieCandidat c2
where c1.c=c2.c and c1.d=c2.d -- ar trebui sa fie diferita de dimensiunea tabelei

insert into CheieCandidat
values
(1,2,3,4) -- cheie duplicat

insert into CheieCandidat
values (1,3,4,5) -- nu respecta dependenta functionala

delete from CheieCandidat

-- count, avg

create table Numbers(n integer)
insert into Numbers(n)
values (2),(null),(3),(1),(2),(1)

delete from Numbers

insert into Numbers(n)
values (5)

select * from Numbers
select avg(n) from Numbers
select count(n) from Numbers
select count(distinct n) from Numbers
select count(n)-max(n) from Numbers

-- verificare inner join (except + intersect)

create table RR(a integer, b integer, c integer)
insert into RR values
(1,2,3),(1,2,4),(3,3,5)

select * from RR

create table SS(a integer, d integer)
insert into SS values
(1,4),(1,7),(3,8)

select * from SS

select RR.a from RR, SS except select RR.a from RR
select RR.a from RR, SS intersect select RR.a from RR

create table G(a integer, b integer)
insert into G values 
(1,2),(1,3),(null,5),(null,1),(2,7)

select distinct a from G where b>0

select a from G where b>0 group by a

delete from G
