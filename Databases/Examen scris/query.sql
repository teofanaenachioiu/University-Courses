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