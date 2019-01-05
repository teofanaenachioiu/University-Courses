;; Definiti o functie care inverseaza o lista impreuna cu toate 
;; sublistele sale de pe orice nivel. De exemplu: 
;; ((2 (4 5)) 1 (4 (9 1) 7)) --> ((7 (1 9) 4) 1 ((5 4) 2))

(defun invers_aux (L col)
	(cond
		((null L) col)
		((listp (car L)) (invers_aux (cdr L) 
				(cons (invers_aux (car L) nil) col)) )
		(T (invers_aux (cdr L) (cons (car L) col)))
		)
	)

(defun invers(L)
	(invers_aux L nil)
	)