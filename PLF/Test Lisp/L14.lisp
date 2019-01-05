;; Definiti o functie care determina suma a doua numere in reprezentare
;; de lista, fara a converti numerele in baza 10. De exemplu:
;; (1 1 1) (2 2 2) -> (3 3 3)

(defun invers(L col)
	(cond 
		((null L) col)
		(T (invers (cdr L) (cons (car L) col)))
		)
	)

(defun suma_aux(L K transp)
	(cond 
		((and (null L) (null K)) nil)
		((null L) (suma_aux nil (cad)))
		((null K) ...)
		()
		)
	)

(defun suma (L K)
	(invers (suma_aux (invers L nil) (invers K nil)))
	)