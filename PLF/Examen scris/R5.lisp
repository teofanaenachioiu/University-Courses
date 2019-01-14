(defun noduri(arb k e)
	(cond
		((and (atom arb)(= k 1)) (list e))
		((atom arb) (list arb))
		(T (list (apply #'append (mapcar #'(lambda(v) (noduri v (- k 1) e)) arb))))
		)
	)