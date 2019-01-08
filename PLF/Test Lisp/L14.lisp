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
		((and (null L) (null K) (= transp 1)) (cons 1 nil))
		((and (null L) (null K)) nil)
		((null L) (cons 
			(mod (+ (car K) transp )10)
			(suma_aux nil (cdr K)
				(floor (+ (car K) transp ) 10)
				)
			)
		)
		((null K) (cons 
			(mod (+ (car L) transp )10)
			(suma_aux (cdr L) nil
				(floor (+ (car L) transp ) 10)
				)
			)
		)
		(T (cons 
			(mod (+ (car K) (car L) transp )10)
			(suma_aux (cdr L) (cdr K) 
				(floor (+ (car K) (car L) transp ) 10)
				)
			)
		)
	)
	)

(defun suma (L K)
	(invers (suma_aux (invers L nil) (invers K nil) 0) nil)
	)