(defun f(L)
	(cond
		((atom L) -1)
		((> (f(car L)) 0) (+ (car L) (f (car L)) (f (cdr L))))
		(T (f(cdr L)))
		)
	)

(defun f1(L)
	(cond
		((atom L) -1)
		(T (lambda (l2)
			(cond
				((> l2 0) (+ (car L) l2 (f1 (cdr L))))
				(T (f1 (cdr L)))
				)
			)
		(f1 (car L)))
		)
	)