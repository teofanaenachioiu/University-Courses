-- Sa se determine cati participanti sunt implicati la fiecare eveniment
-- [ COUNT, GROUP BY, ORDER BY ]
SELECT count(E.Eid) as 'Nr voluntari',E.Denumire as 'Eveniment' 
FROM Evenimente E
INNER JOIN Inscrieri I
ON E.Eid=I.Eid
INNER JOIN Grupe G
ON G.Gid=G.Gid
INNER JOIN Participanti P
ON G.Gid=P.Gid
GROUP BY E.Denumire, E.Eid
ORDER BY E.Eid

-- Sa se deternine cate persoane isi sarbatoresc aniversarea in fiecare zi
SELECT Count(DAY(P.Data_n)) as 'Nr persoane', DAY(P.Data_n)as 'Ziua'
FROM Participanti P, Grupe G, Moderatori M, Inscrieri I, Evenimente E, Contracte C, Voluntari V
WHERE P.Gid=G.Gid and M.Mid=G.Gid and G.Gid=I.Gid and I.Eid=E.Eid and E.Eid=C.Eid and C.Vid=V.Vid
GROUP BY DAY(P.Data_n)
HAVING DAY(P.Data_n) between 19 and 22

