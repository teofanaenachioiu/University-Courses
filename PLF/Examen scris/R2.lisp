(defun succesor(L)
	(cond
		((null L) nil)
		((numberp (car L)) (cons (+ (Car L) 1) (succesor (cdr L))))
		((atom (car L)) (cons (car L) (succesor (cdr L))))
		(T(cons(succesor (car L)) (succesor (cdr L))))
		)
	)

(defun succesor1 (L)
	(cond 
		((numberp L) (+ L 1))
		((atom L) L)
		((listp L) (cons (succesor1 (car L)) (succesor1 (cdr L))))
		)
	)

(defun maxim (L)
 	(cond
 		((numberp L) L)
		((atom L) -1)
 		(t (apply #'max
				(mapcar #'maxim L)
 				)
 		)
 	)
)

(defun nrSub (L niv)
	(cond
		((atom L) 0)
		((and (listp L) (=(mod niv 2) 1) (= (mod (maxim L) 2) 0)) 
			(+ 1 (apply #'+ (mapcar #'(lambda (Li)(nrSub Li (+ niv 1) ) ) L)))
			)
		(T (apply #'+ (mapcar #'(lambda (Li)(nrSub Li (+ niv 1) ) ) L)))
		)
	
