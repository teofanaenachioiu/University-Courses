;;; Definiti o functie care substituie 
;;; un element E prin elementele unei liste
;;; L1 la toate nivelurile unei liste date L.

(defun substituie (L e Ls)
	(cond 
		( (NULL L) NIL )
		( (equal (CAR L) e)
			(APPEND Ls (substituie (CDR L) e Ls) )
			;(CONS Ls (substituie (CDR L) e Ls) )
		)
		( (LISTP (CAR L)) 
			(APPEND (CONS (substituie (CAR L) e Ls) NIL) (substituie (CDR L) e Ls) )
		)
		(T (APPEND (CONS (CAR L) NIL) (substituie (CDR L) e Ls) )
			)
	)
)