DECLARE @nume VARCHAR(50), @prenume VARCHAR(50), @oras VARCHAR(50); 
DECLARE cursorpersoane CURSOR FAST_FORWARD FOR 
SELECT prenume, nume , oras FROM Persoane;
OPEN cursorpersoane;
FETCH NEXT FROM cursorpersoane INTO @prenume, @nume , @oras;
WHILE  @@FETCH_STATUS=0
BEGIN
PRINT @prenume +' '+@nume + ' s-a nascut nascut in orasul '+@oras;
FETCH NEXT FROM cursorpersoane INTO @prenume, @nume , @oras;
END
CLOSE cursorpersoane;
DEALLOCATE cursorpersoane;

CREATE TABLE Persoane(
	nume VARCHAR (50),
	prenume VARCHAR (50),
	oras VARCHAR (50)
);