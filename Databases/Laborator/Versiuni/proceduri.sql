CREATE PROCEDURE main
	@vers INT 
AS BEGIN
	DECLARE @curent INT
	DECLARE @proc VARCHAR(3)

	SELECT @curent=TVersiune
	FROM Versiuni

	IF @vers <0 or @vers >5
	BEGIN
		 PRINT 'Parametru gresit'
		 END
	ELSE
	BEGIN
		IF @curent = @vers
		BEGIN
			PRINT 'Baza de date este deja in versiunea '+CONVERT(VARCHAR(2),@curent)
			END
		IF @curent < @vers
			WHILE @curent!= @vers
			BEGIN
				SET @curent=@curent+1
				SET @proc='D'+CONVERT(VARCHAR(2),@curent)
				PRINT @proc
				EXEC @proc
				END
		ELSE 
			WHILE @curent!= @vers
			BEGIN
				SET @proc='I'+CONVERT(VARCHAR(2),@curent)
				PRINT @proc
				EXEC @proc
				SET @curent=@curent-1
				END
		UPDATE Versiuni
		SET TVersiune=@vers
	END	
END
