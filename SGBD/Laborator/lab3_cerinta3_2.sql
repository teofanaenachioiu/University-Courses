-- Tabela Voluntari:
-- Vid int primary key, Nume varchar(50) not null, Data_n date
--------------------------------------------------------------

					-- TRANZACTIA 2

-------------------- DIRTY READS --------------------

SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED -- dirty reads
--SET TRANSACTION ISOLATION LEVEL READ COMMITTED -- solution dirty reads
BEGIN TRAN
SELECT * FROM Voluntari
WAITFOR DELAY '00:00:15'
SELECT * FROM Voluntari
COMMIT TRAN


-------------------- NON-REPEATABLE READS --------------------

--SET TRANSACTION ISOLATION LEVEL READ COMMITTED -- non-repetable reads
SET TRANSACTION ISOLATION LEVEL REPEATABLE READ -- solution non-repetable reads
BEGIN TRAN
SELECT * FROM Voluntari
WAITFOR DELAY '00:00:05'
SELECT * FROM Voluntari
COMMIT TRAN


-------------------- PHANTOM READS --------------------

SET TRANSACTION ISOLATION LEVEL REPEATABLE READ -- phantom reads
--SET TRANSACTION ISOLATION LEVEL SERIALIZABLE -- solution phantom reads
BEGIN TRAN
SELECT * FROM Voluntari
WAITFOR DELAY '00:00:05'
SELECT * FROM Voluntari
COMMIT TRAN


-------------------- DEADLOCK --------------------

begin tran
update Evenimente set Denumire='Evenimente TRAN 2' where Eid=30
-- blocare exclusiva tabela Evenimente
waitfor delay '00:00:10'
update Voluntari set Nume='Voluntari TRAN 2' where Vid=200 
-- tranzactie blocata de TRAN 1
commit tran

select * from Voluntari
select * from Evenimente