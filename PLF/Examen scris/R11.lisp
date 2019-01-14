(defun exista(x L)
	(cond
		((null L) nil)
		((and (atom (car L))(equal x (car L)))T)
		((atom (car L)) (exista x (cdr L)))
		(t(or (exista x (car L))(exista x (cdr L))))
	)
	)

(defun cale(arb x)
	(cond 
		((null arb) nil)
		((atom (car arb)) (cons (car arb) (cale (cdr arb) x)))
		((exista x (car arb)) (cale (car arb) x))
		(T (cale (cdr arb) x))
		)

	)

