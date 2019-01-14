(defun inserare(e L)
	(cond
		((null L) nil)
		(T (cons (cons e (car L)) (inserare e (cdr L))))
	)
)

(defun submultimi (L)
	(cond
		((null L) nil)
		(T ((lambda (V) 
				(append V (inserare (car L) V) ) )
			(submultimi (cdr L))
			)
		)
	)
	)