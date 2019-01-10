;; Definiti o functie care determina diferenta a 2 numere in reprezentare
;; de  lista, fara a transforma listele in numere. De exemplu:
;; (3 3 3) (1 1 1) -> (2 2 2)

(defun faraZero(L)
	(cond 
		((null L) nil)
		((= (Car L) 0) (faraZero (cdr L)))
		(T (cons (car L) (cdr L)))
		)
	)

(defun invers(L col)
	(cond 
		((null L) col)
		(T (invers (cdr L) (cons (car L) col)))
		)
	)

(defun dif(L K imp)
	(cond 
		((and (null L) (null K)) nil)
		((null K) (cons 
			(mod (- (car L) imp )10)
			(dif (cdr L) nil
				(floor (- (car L) imp ) 10)
				)
			)
		)
		(T (cons 
			(mod (- (car L) (car K) imp )10)
			(dif (cdr L) (cdr K) 
				(floor (- (car L) (car K) imp ) 10)
				)
			)
		)
	)
	)

(defun diferenta (L K)
	(faraZero (invers (dif (invers L nil) (invers K nil) 0) nil))
	)