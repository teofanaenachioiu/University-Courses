;; Dandu-se o multime de numere intregi reprezentata sub forma unei
;; liste liniare, se cere multimea submultimilor listei. De exemplu:
;; (1 2 3) --> (NIL (1) (2) (3) (1 2) (1 3) (2 3) (1 2 3))

(defun insereaza (E L)
	(cond
		((null L) nil)
		(T (cons (cons E (car L)) (insereaza E (cdr L))))
		)
	)

(defun submultimi(L)
	(cond
		((null L) (list nil))
		(T
		;;(append (submultimi (cdr L)) (insereaza (car L) (submultimi (cdr L))) )
		(
			(lambda (V)
				(append V (insereaza (car L) V))
				)
			(submultimi (cdr L))
			)
		)
	)
)