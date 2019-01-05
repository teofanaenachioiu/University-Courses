;; Definiti o functie care sorteaza fara pastrarea dublurilor 
;; o lista liniara. De exemplu:
;; (5 1 3 4 2 1 3) --> (1 2 3 4 5)

(defun elimin(E L)
	(cond
	((null L) nil)
	((equal E (car L)) (elimin E (cdr L)))
	(T (cons (car L) (elimin E (cdr L))))
	)
)

(defun minim (L mini)
	(cond
		((null L) mini)
		((< (car L) mini) (minim (cdr L) (car L)))
		(T (minim (cdr L) mini))
		)
	)

(defun sorteaza(L)
	(cond
		((null L) nil)
		(T 
			(
				(lambda (V)
					(append (list V) (sorteaza (elimin V L)))
				)
				(minim L (car L))
			)
		)
	)
)