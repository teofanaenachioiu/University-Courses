-- 1. Sa se determine cati participanti sunt implicati la fiecare eveniment
-- GROUP BY
-- relatie many-to-many
-- extragere din 3 tabele

SELECT COUNT(E.Eid) as 'Numar participanti',E.Denumire as 'Eveniment' 
FROM Evenimente E
INNER JOIN Inscrieri I
ON E.Eid=I.Eid
INNER JOIN Grupe G
ON G.Gid=I.Gid
INNER JOIN Participanti P
ON G.Gid=P.Gid
GROUP BY E.Denumire, E.Eid
ORDER BY E.Eid

-- 2. Sa se deternine cate persoane isi sarbatoresc aniversarea in fiecare zi a taberei (19-22 iulie)
-- GROUP BY, HAVING
-- relatie many-to-many
-- extragere din 3 tabele

SELECT SUM(subquery.[nr persoane]) AS 'Numar persoane', ziua AS 'Ziua de nastere' 
FROM (

SELECT COUNT(DAY(P.Data_n)) AS 'nr persoane', DAY(P.Data_n) AS 'ziua'
FROM Participanti P
GROUP BY DAY(P.Data_n),MONTH(P.Data_n)
HAVING MONTH(P.Data_n)='7' AND DAY(P.Data_n) BETWEEN 19 AND 22
UNION ALL

SELECT COUNT(DAY(M.Data_n)) AS 'nr persoane', DAY(M.Data_n) AS 'ziua'
FROM Moderatori M
GROUP BY DAY(M.Data_n),MONTH(M.Data_n)
HAVING MONTH(M.Data_n)='7' AND DAY(M.Data_n) BETWEEN 19 AND 22
UNION ALL

SELECT COUNT(DAY(V.Data_n)) AS 'nr persoane', DAY(V.Data_n) AS 'ziua'
FROM Voluntari V
GROUP BY DAY(V.Data_n),MONTH(V.Data_n)
HAVING MONTH(V.Data_n)='7' AND DAY(V.Data_n) BETWEEN 19 AND 22 

) AS subquery
GROUP BY subquery.ziua

-- 3. Sa se determine toate asociatiile cazate in 'cort'
-- WHERE, DISTINCT
-- relatie many-to-many
-- extragere din 3 tabele

SELECT DISTINCT A.Denumire as 'Asociatia'
FROM Asociatii A
INNER JOIN Cereri Ce ON Ce.Aid=A.Aid
INNER JOIN Cazari C ON C.Cid=Ce.Cid
WHERE C.Locatie LIKE 'Cort%' 

-- 4. Sa se determine numele si numarul de telefon al insotitorilor asociatiilor 
-- care au participanti implicati la evenimentul 'Vanatoare de comori'
-- WHERE, DISTINCT
-- relatie many-to-many
-- extragere din mai mult de doua tabele

SELECT DISTINCT I.Nume, I.Telefon
FROM Insotitori I
INNER JOIN Asociatii A ON A.Aid=I.Iid
INNER JOIN Participanti P ON P.Aid=A.Aid
INNER JOIN Grupe G ON P.Gid=G.Gid
INNER JOIN Inscrieri S ON S.Gid=G.Gid
INNER JOIN Evenimente E ON E.Eid=S.Eid
WHERE E.Denumire='Vanatoare de comori'

-- 5. Sa se determine toti participantii (+numele asociatiilor,insotitorilor) care au varsta sub 18 ani
-- WHERE
-- extragere din mai mult de doua tabele

SELECT P.Nume, A.Denumire,I.Nume
FROM Insotitori I
INNER JOIN Asociatii A ON A.Aid=I.Iid
INNER JOIN Participanti P ON P.Aid=A.Aid
WHERE DATEDIFF(day, P.Data_n,'2018-07-19')<DATEDIFF(day, '2000-07-19','2018-07-19')

-- 6. Sa se determine cati participanti la evenimentul 'Foc de tabara' sunt cazati la 'Colegiu'
-- WHERE, GROUP BY
-- relatie many-to-many
-- extragere din mai mult de doua tabele

SELECT COUNT(P.Nume) AS 'Numar participanti', A.Denumire as 'Asociatia'
FROM Participanti P 
FULL JOIN Grupe G ON P.Gid=G.Gid
FULL JOIN Inscrieri I ON I.Gid=G.Gid
FULL JOIN Evenimente E ON E.Eid=I.Eid
FULL JOIN Asociatii A ON P.Aid=A.Aid
FULL JOIN Cereri Ce ON Ce.Aid=A.Aid
FULL JOIN Cazari C ON C.Cid=Ce.Cid
WHERE E.Denumire='Spectacol' and C.Locatie='Cabana' 
GROUP BY A.Denumire

-- 7. Sa se determine voluntarul (sau voluntarii) implicati in cele mai multe evenimente
-- relatie many-to-many
-- extragere din mai mult de doua tabele
SELECT V.Nume
FROM Evenimente E
INNER JOIN Contracte C ON C.Eid=E.Eid
INNER JOIN Voluntari V ON V.Vid=C.Vid
----


-- 8. Sa se determine cati participanti au <18 si cati au >=18 ani la evenimentele care incep la ora 20:00
 
-- 9. Sa se determine varsta medie din fiecare grupa

-- 10. Sa se afiseze totate grupele care au participat la proiectia de film si la spectacol