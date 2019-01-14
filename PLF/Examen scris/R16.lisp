(defun suma(L)
	(cond
		((numberp L)  L)
		((atom L)  0)
		(T (apply #'+ (mapcar #'suma L)))		
))

(defun num(L k)
	(cond
		((atom L) 0)
		((and (= (mod k 2) 1) (= (mod (suma K) 2) 0)) 
			(+ 1 (apply #'+ (mapcar #'(lambda (l) (num l (+ k 1))) L)))
			)
		(T (apply #'+ (mapcar #'(lambda (l) (num l (+ k 1))) L)))
		)
	)