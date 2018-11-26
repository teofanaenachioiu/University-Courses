;;; Definiti o functie care interclaseaza 
;;; cu pastrarea dublurilor doua liste
;;; liniare sortate.

(defun interclasare(L1 L2)
	(cond
		( (AND (NULL L1 ) (NULL L2) ) NIL )
		( (NULL L1) L2 )
		( (NULL L2) L1 )
		( (< (CAR L1) (CAR L2) ) 
			(APPEND (CONS (CAR L1) NIL) (interclasare (CDR L1) L2) ) 
			)
		( (>= (CAR L1) (CAR L2) ) 
			(APPEND (CONS (CAR L2) NIL) (interclasare L1 (CDR L2) ) ) 
			)
	)
)