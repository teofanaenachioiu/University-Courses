use Ceainarie 
go

INSERT INTO MARCI(Nume, Rating) VALUES ('Fares',1),('Lipton',2)
SELECT * FROM MARCI

INSERT INTO CEAIURI(Nume,Temperatura,Mid) VALUES ('Verde',99,10)
INSERT INTO CEAIURI VALUES ('Negru',25,10)
SELECT * FROM CEAIURI

INSERT INTO INGREDIENTE VALUES ('Apa','Robinet',100),('Tei','Copac',50)
SELECT * FROM INGREDIENTE

INSERT INTO CONTINUTURI(Cid, Iid, CantitateC) VALUES(3,3,100),(3,4,20)
SELECT * FROM CONTINUTURI

-- 1. Sa se afiseze ceaiurile fiecarei marci avand numele marcii 'Nestie'

-- varianta 1
SELECT M.Nume, C.Nume
FROM MARCI M, CEAIURI C
WHERE M.Mid=C.Mid and M.Nume='Nestie'

-- varianta 2
SELECT M.Nume, C.Nume
FROM MARCI M INNER JOIN CEAIURI C ON M.Mid=C.Mid

-- varianta 3
SELECT M.Nume, C.Nume
FROM MARCI M LEFT OUTER JOIN CEAIURI C ON M.Mid=C.Mid

-- varianta 4
SELECT M.Nume, C.Nume
FROM MARCI M RIGHT OUTER JOIN CEAIURI C ON M.Mid=C.Mid

-- varianta 5
SELECT M.Nume, C.Nume
FROM MARCI M FULL JOIN CEAIURI C ON M.Mid=C.Mid

-- 2. Sa se afiseze ingredientele fiecarui ceai a carui numae incepe cu litera A, 
-- nu are termperatura mai mare de 50 de grade (ordaonate descrescatoare dupa numele ceaiurilor)

SELECT DISTINCT C.Nume, I.Nume
FROM CEAIURI C 
INNER JOIN CONTINUTURI Co ON C.Cid=Co.Cid 
INNER JOIN INGREDIENTE I ON Co.Iid=I.Iid
WHERE C.Nume LIKE 'A%' and C.Temperatura<=50

-- 3. Sa se afiseze pentru fiecare marca temperatura medie a ceaiurilor.

SELECT M.Mid, avg(Temperatura) AS 'Medie temperatura'
FROM MARCI M 
INNER JOIN CEAIURI C ON C.Mid=M.Mid
GROUP BY M.Mid
--HAVING AVG(Temperatura)>50
HAVING M.Mid<=10 --temperatura medie pentru primele 10 marci