;; Definiti o functie care determina succesorul unui numar reprezentat
;; cifra cu cifra intr-o lista liniara, fara a converti numarul in baza
;; 10. De  exemplu:
;; (1 9 3 5 9 9) --> (1 9 3 6 0 0)

(defun invers (L col)
	(cond
		((null L) col)
		(T (invers (cdr L) (cons (car L) col)))
		)
	)

(defun add (L transp)
	(cond
		((and (null l) (= transp 1)) (list 1))
		((and (null l) (= transp 0)) nil)
		(T (cons (mod (+ (car L) transp) 10) 
			(add (cdr L) (floor (+ (car L) transp ) 10)))
	)
	)
	)

(defun succesor(L)
	(invers (add (invers L nil) 1) nil) 
)