CREATE PROCEDURE I5
AS
BEGIN
	ALTER TABLE Evenimente
	DROP CONSTRAINT FK_Locatii
END
GO