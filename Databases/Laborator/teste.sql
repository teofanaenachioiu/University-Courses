--- Tabela TABLES --------------------------------------------------
INSERT INTO Tables(Name) VALUES 
('Voluntari'),
('Participanti'),
('Contracte');
GO

--- Creare VIEW-uri -------------------------------------------------
CREATE OR ALTER VIEW VVoluntari
AS
SELECT  Vid,Nume,Data_n
FROM Voluntari
GO

CREATE OR ALTER VIEW VParticipanti
AS
SELECT P.Nume, G.Denumire
FROM Participanti P
INNER JOIN Grupe G ON G.Gid=P.Gid
GO

CREATE OR ALTER VIEW VContracte
AS
SELECT E.Denumire, Count(*) as 'Nr voluntari'
FROM Evenimente E
INNER JOIN Contracte C ON C.Eid=E.Eid
INNER JOIN Voluntari V ON C.Vid=V.Vid
GROUP BY E.Denumire
GO


--- Tabela VIEW ------------------------------------------------------
INSERT INTO Views VALUES
('VVoluntari'),
('VParticipanti'),
('VContracte')


--- Tabela Tests -----------------------------------------------------
INSERT INTO Tests(Name) VALUES 
('Inserare10'),
('Inserare100'),
('Inserare1000'),
('Sterge10'),
('Sterge100'),
('Sterge1000'),
('Evaluare')
GO


--- Tabela TESTTABLES ------------------------------------------------
INSERT INTO TestTables VALUES
(1, 1, 10, 1),
(2, 1, 100, 1),
(3, 1, 1000, 1),
(1, 2, 10, 2),
(2, 2, 100, 2),
(3, 2 ,1000, 2),
(1, 3, 10, 3),
(2, 3, 100, 3),
(3, 3, 1000, 3),

(4, 1, 10, 3),
(5, 1, 100, 3),
(6, 1, 1000, 3),
(4, 2, 10, 2),
(5, 2, 100, 2),
(6, 2 ,1000, 2),
(4, 3, 10, 1),
(5, 3, 100, 1),
(6, 3, 1000, 1)
GO


--- Tabela TESTVIEWS ---------------------------------------------------
INSERT INTO TestViews VALUES 
(7,1),
(7,2),
(7,3)
GO


--- Inserare in tabela VOLUNTARI ---
CREATE or ALTER PROCEDURE InsertVoluntari (@rows int)
AS
BEGIN
	DECLARE @id int
	DECLARE @nume VARCHAR(50)
	DECLARE @i int
	DECLARE @lastId int
	SET @nume='NumeVoluntar'
	SET @id=2000
	SET @i=1

	WHILE @i<=@rows
	BEGIN
		SET @id=2000+@i
		SELECT TOP 1 @lastId = Voluntari.Vid FROM dbo.Voluntari ORDER BY Voluntari.Vid DESC
		IF @lastId >2000
			SET @id=@lastId+1			
		INSERT INTO Voluntari VALUES (@id,@nume,null)
		SET @i=@i+1
	END

END
GO

--- Stergere din tabela VOLUNTARI ---
CREATE OR ALTER PROCEDURE DeleteVoluntari(@rows int)
AS
BEGIN
	DECLARE @id int
	DECLARE @i int
	DECLARE @lastId int

	SET @id=2000
	SET @i=@rows

	WHILE @i>0
	BEGIN
		SET @id=2000+@i
		SELECT TOP 1 @lastId = Voluntari.Vid FROM dbo.Voluntari ORDER BY Voluntari.Vid DESC
		IF @lastId >@id
			SET @id=@lastId		
		DELETE FROM Voluntari WHERE Voluntari.Vid=@id
		SET @i=@i-1
	END
END
GO

--- Inserare in tabela PARTICIPANTI ---
CREATE or ALTER PROCEDURE InsertParticipanti (@rows int)
AS
BEGIN
	DECLARE @id int
	DECLARE @nume VARCHAR(50)
	DECLARE @data_n DATE 
	DECLARE @i int
	DECLARE @lastId int

	SET @nume='NumeParticipant'
	SET @data_n='1998-04-11'
	SET @id=2000
	SET @i=1

	WHILE @i<=@rows
	BEGIN
		SET @id=2000+@i
		SELECT TOP 1 @lastId = Participanti.Pid FROM dbo.Participanti ORDER BY Participanti.Pid DESC
		IF @lastId >2000
			SET @id=@lastId+1			
		INSERT INTO Participanti VALUES (@id,1,101,@nume,@data_n)
		SET @i=@i+1
	END

END
GO

--- Stergere din tabela PARTICIPANTI ---
CREATE OR ALTER PROCEDURE DeleteParticipanti(@rows int)
AS
BEGIN
	DECLARE @id int
	DECLARE @i int
	DECLARE @lastId int

	SET @id=2000
	SET @i=@rows

	WHILE @i>0
	BEGIN
		SET @id=2000+@i
		SELECT TOP 1 @lastId = Participanti.Pid FROM dbo.Participanti ORDER BY Participanti.Pid DESC
		IF @lastId >@id
			SET @id=@lastId		
		DELETE FROM Participanti WHERE Participanti.Pid=@id ON CASCADE
		SET @i=@i-1
	END
END
GO

--- Inserare in tabela CONTRACTE ---
CREATE or ALTER PROCEDURE InsertContracte (@rows int)
AS
BEGIN
	DECLARE @i int
	SET @i=@rows

	exec InsertVoluntari @rows
	--- cursorul
	DECLARE @idV int, @Nume VARCHAR(50), @Data_n DATE; 
	DECLARE cursorpersoane CURSOR SCROLL FOR 
	SELECT Vid, Nume , Data_n FROM Voluntari;
	OPEN cursorpersoane;
	FETCH LAST FROM cursorpersoane INTO @idV, @Nume , @Data_n;
		
	WHILE @i>0 AND @@FETCH_STATUS=0
		BEGIN
		INSERT INTO Contracte VALUES (1,@idV)
		FETCH PRIOR FROM cursorpersoane INTO @idV, @Nume , @Data_n;
		SET @i=@i-1
	END

	CLOSE cursorpersoane;
	DEALLOCATE cursorpersoane;
END
GO

--- Stergere din tabela CONTRACTE ---
CREATE OR ALTER PROCEDURE DeleteContracte(@rows int)
AS
BEGIN
	DECLARE @idE int
	DECLARE @i int
	DECLARE @idV int

	SET @i=@rows
	SET @idE=1

	WHILE @i>0
	BEGIN
		SELECT TOP 1 @idV = Voluntari.Vid FROM dbo.Voluntari ORDER BY Voluntari.Vid DESC
		IF @idV >2000
		BEGIN
			DELETE FROM Contracte WHERE Contracte.Eid=1 AND Contracte.Vid=@idV
			exec DeleteVoluntari 1
			END
		SET @i=@i-1
	END
END
GO

--- Testele ------------------------------------------------------------
CREATE OR ALTER PROCEDURE Inserare10 (@Tabela VARCHAR(20))
AS
BEGIN
	IF @Tabela='Voluntari'
	exec InsertVoluntari 10
	IF @Tabela='Participanti'
	exec InsertParticipanti 10
	IF @Tabela='Contracte'
	exec InsertContracte 10
	else PRINT 'Nume invalid'
END
GO

CREATE OR ALTER PROCEDURE Inserare100 (@Tabela VARCHAR(20))
AS
BEGIN
	IF @Tabela='Voluntari'
	exec InsertVoluntari 100
	IF @Tabela='Participanti'
	exec InsertParticipanti 100
	IF @Tabela='Contracte'
	exec InsertContracte 100
	else PRINT 'Nume invalid'
END
GO

CREATE OR ALTER PROCEDURE Inserare1000 (@Tabela VARCHAR(20))
AS
BEGIN
	IF @Tabela='Voluntari'
	exec InsertVoluntari 1000
	IF @Tabela='Participanti'
	exec InsertParticipanti 1000
	IF @Tabela='Contracte'
	exec InsertContracte 1000
	else PRINT 'Nume invalid'
END
GO

CREATE OR ALTER PROCEDURE Sterge10 (@Tabela VARCHAR(20))
AS
BEGIN
	IF @Tabela='Voluntari'
	exec DeleteVoluntari 10
	IF @Tabela='Participanti'
	exec DeleteParticipanti 10
	IF @Tabela='Contracte'
	exec DeleteContracte 10
	else PRINT 'Nume invalid'
END
GO

CREATE OR ALTER PROCEDURE Sterge100 (@Tabela VARCHAR(20))
AS
BEGIN
	IF @Tabela='Voluntari'
	exec DeleteVoluntari 100
	IF @Tabela='Participanti'
	exec DeleteParticipanti 100
	IF @Tabela='Contracte'
	exec DeleteContracte 100
	else PRINT 'Nume invalid'
END
GO

CREATE OR ALTER PROCEDURE Sterge1000 (@Tabela VARCHAR(20))
AS
BEGIN
	IF @Tabela='Voluntari'
	exec DeleteVoluntari 1000
	IF @Tabela='Participanti'
	exec DeleteParticipanti 1000
	IF @Tabela='Contracte'
	exec DeleteContracte 1000
	else PRINT 'Nume invalid'
END
GO

CREATE OR ALTER PROCEDURE Evaluare (@View VARCHAR(20))
AS
BEGIN
	IF @View='Voluntari'
	select * from VVoluntari
	IF @View='Participanti'
	select * from VParticipanti
	IF @View='Contracte'
	select * from VContracte
	else PRINT 'Nume invalid'
END
GO

--- Procedura principala ----------------------------------------------
CREATE OR ALTER PROCEDURE Main (@Tabela VARCHAR(20))
AS
BEGIN
	DECLARE @t1 datetime, @t2 datetime, @t3 datetime
	DECLARE @desc NVARCHAR(2000)

	DECLARE @testInserare VARCHAR(20)
	DECLARE @testSterge VARCHAR(20)
	DECLARE @nrRows int
	DECLARE @idTest int

	SET @nrRows=1000
	SET @testInserare='Inserare' + CONVERT(VARCHAR (5),@nrRows)
	SET @testSterge='Sterge'++ CONVERT(VARCHAR (5),@nrRows)


	if @Tabela='Participanti'
		BEGIN
			SET @t1 =GETDATE()
			exec @testInserare Participanti
			exec @testSterge Participanti
			SET @t2 =GETDATE()
			exec Evaluare Participanti
			SET @t3 =GETDATE()
			SET @desc=N'Testul s-a facut '+@testInserare+', '+@testSterge+', EvaluareView pe tabela '+@Tabela
			INSERT INTO TestRuns VALUES (@desc,@t1,@t3)
			SELECT TOP 1 @idTest=T.TestRunID FROM dbo.TestRuns T ORDER BY T.TestRunID DESC
			INSERT INTO TestRunTables VALUES (@idTest,1,@t1,@t2)
			INSERT INTO TestRunViews VALUES (@idTest,1,@t2,@t3)
		END
	if @Tabela='Contracte'
		BEGIN
			SET @t1 =GETDATE()
			exec @testInserare Contracte
			exec @testSterge Contracte
			SET @t2 =GETDATE()
			exec Evaluare Contracte
			SET @t3 =GETDATE()
			SET @desc=N'Testul s-a facut '+@testInserare+', '+@testSterge+', EvaluareView pe tabela '+@Tabela
			INSERT INTO TestRuns VALUES (@desc,@t1,@t3)
			SELECT TOP 1 @idTest=T.TestRunID FROM dbo.TestRuns T ORDER BY T.TestRunID DESC
			INSERT INTO TestRunTables VALUES (@idTest,3,@t1,@t2)
			INSERT INTO TestRunViews VALUES (@idTest,3,@t2,@t3)
		END
	if @Tabela='Voluntari'
		BEGIN
			SET @t1 =GETDATE()
			exec @testInserare Voluntari
			exec @testSterge Voluntari
			SET @t2 =GETDATE()
			exec Evaluare Voluntari
			SET @t3 =GETDATE()
			SET @desc=N'Testul s-a facut '+@testInserare+', '+@testSterge+', EvaluareView pe tabela '+@Tabela
			INSERT INTO TestRuns VALUES (@desc,@t1,@t3)
			SELECT TOP 1 @idTest=T.TestRunID FROM dbo.TestRuns T ORDER BY T.TestRunID DESC
			INSERT INTO TestRunTables VALUES (@idTest,2,@t1,@t2)
			INSERT INTO TestRunViews VALUES (@idTest,2,@t2,@t3)
		END
	ELSE PRINT 'Tabela invalida'
END


exec Main Voluntari
exec Main Participanti
exec Main Contracte

select * from TestRuns
select * from TestRunTables
select * from TestRunViews


--- Pentru cand mai fac chestii naspa. Sa le sterg rapid :D
DROP TABLE TestRunViews
DROP TABLE TestRunTables
DROP TABLE TestRuns
DROP TABLE TestTables
DROP TABLE TestViews
DROP TABLE Tests
DROP TABLE Tables
DROP TABLE Views