-- Tabela Voluntari:
-- Vid int primary key, Nume varchar(50) not null, Data_n date
--------------------------------------------------------------

select * from Voluntari where Vid= 200
insert into Voluntari values (200, 'VolutarulNou',null)
UPDATE Voluntari SET Nume='VolutarulNou'

					-- TRANZACTIA 1 --

-------------------- DIRTY READS --------------------

BEGIN TRANSACTION
UPDATE Voluntari SET Nume='VoluntarDirtyReads'
WHERE Vid = 200
WAITFOR DELAY '00:00:10'
ROLLBACK TRANSACTION


-------------------- NON-REPEATABLE READS --------------------

declare @vid int;
select top 1 @vid = Vid from Voluntari order by Vid desc
set @vid=@vid+1

INSERT INTO Voluntari(Vid,Nume, Data_n) VALUES (@vid,'VoluntarInserted',null)
BEGIN TRAN
WAITFOR DELAY '00:00:05'
UPDATE Voluntari SET Nume='VolunarUpdated' WHERE Vid = @vid
COMMIT TRAN


-------------------- PHANTOM READS --------------------

declare @vid int;
select top 1 @vid = Vid from Voluntari order by Vid desc
set @vid=@vid+1

BEGIN TRAN
WAITFOR DELAY '00:00:04'
INSERT INTO Voluntari(Vid, Nume,Data_n) VALUES (@vid,'VoluntarPhantom',null) 
COMMIT TRAN


-------------------- DEADLOCK --------------------

SET DEADLOCK_PRIORITY HIGH -- solution deadlock
begin tran
update Voluntari set Nume='Voluntari TRAN 1' where Vid=200 
-- blocare exclusiva tabela Volunari
waitfor delay '00:00:10'
update Evenimente set Denumire='Evenimente TRAN 1' where Eid=30
-- tranzactie blocata de TRAN 2
commit tran

select * from Voluntari
select * from Evenimente