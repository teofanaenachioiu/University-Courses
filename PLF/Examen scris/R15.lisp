(defun nrNiv(L k)
	(cond
		((null L) k)
		((atom (car L)) (nrNiv (cdr L) k))
		(T (max (nrNiv (car L) (+ k 1 )) (nrNiv (cdr L) k)))		
	)
)

(defun numara(L)
	(cond 
		((atom L) 0)
		((= (mod (nrNiv L 1) 2 ) 0) (+ 1 (apply #'+ (mapcar #'numara L)) ))
		(T (apply #'+ (mapcar #'numara L)))
		)
	)