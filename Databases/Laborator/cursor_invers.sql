DECLARE @idV int, @Nume VARCHAR(50), @Data_n DATE; 
		DECLARE cursorpersoane CURSOR SCROLL FOR 
		SELECT Vid, Nume , Data_n FROM Voluntari;
		OPEN cursorpersoane;
		FETCH LAST FROM cursorpersoane INTO @idV, @Nume , @Data_n;
		
		WHILE  @@FETCH_STATUS=0
		BEGIN
		--PRINT 'da';
		PRINT CONVERT(VARCHAR(5),@idV) +' Nume: '+@Nume;
		FETCH PRIOR FROM cursorpersoane INTO @idV, @Nume , @Data_n;
		END
		CLOSE cursorpersoane;
		DEALLOCATE cursorpersoane;
		GO