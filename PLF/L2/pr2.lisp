(defun arbore(L nrv nrm)
	(cond
		((equal nrv (+ nrm 1)) (LIST L))
		(T (APPEND (CONS (LIST (CAR L) NIL) (arbore (CDDR L) (+ nrv 1) (+ nrm (CADR L)) ))
		)
	)
	)
	) 