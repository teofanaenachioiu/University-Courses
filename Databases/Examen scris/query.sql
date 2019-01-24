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