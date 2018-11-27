INSERT INTO Tables(Name) VALUES 
('Insotitori'),
('Asociatii'),
('Cereri');
GO


CREATE OR ALTER VIEW VParticipanti
AS
SELECT  Pid,Nume,Data_n
FROM Participanti
GO

CREATE OR ALTER VIEW VVoluntariEvenimente
AS
SELECT Evenimente.Denumire,Count(*) as 'Nr Voluntari'
FROM Voluntari
INNER JOIN Contracte ON Contracte.Vid=Voluntari.Vid
INNER JOIN Evenimente ON Evenimente.Eid=Contracte.Eid
GROUP BY Evenimente.Denumire
GO

CREATE OR ALTER VIEW VModeratoriGrupe
AS
SELECT Moderatori.Nume, Grupe.Denumire
FROM Moderatori
INNER JOIN Grupe ON Grupe.Gid=Moderatori.Mid
GO

INSERT INTO Views VALUES
('VParticipanti'),
('VVoluntariEvenimente'),
('VModeratoriGrupe')

INSERT INTO Tests(Name) VALUES 
('Inserare10'),
('Inserare100'),
('Inserare1000'),
('Sterge10'),
('Sterge100'),
('Sterge1000'),
('Select')

SELECT * FROM Tables
SELECT * FROM Tests

INSERT INTO TestViews VALUES 
(16, 1),
(16, 2),
(16, 3)
