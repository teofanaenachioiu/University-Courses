(defun arboreDr(L nrv nrm)
	(cond
		((equal nrv (+ nrm 1))  L)
		((NULL L) NIL)
		(T (arboreDr (CDDR L) (+ nrv 1) (+ nrm (CADR L)) ))
	)
)

(defun arboreStg(L nrv nrm)
	(cond
		((equal nrv (+ nrm 1)) NIL)
		((NULL L) NIL)
		(T (APPEND (LIST (CAR L) (CADR L)) 
			(arboreStg (CDDR L)  (+ nrv 1) (+ nrm (CADR L)))
			)	
		)

	)
)

(defun noduriNivel(L k)
	(cond
		((NULL L) NIL)
		((equal k 1) (LIST (CAR L))) 
		(T 
			(APPEND (noduriNivel (arboreStg (CDDR L) 0 0) (- k 1)) 
				(noduriNivel (arboreDr (CDDR L) 0 0) (- k 1)))
			)
		

		)
	)
	 