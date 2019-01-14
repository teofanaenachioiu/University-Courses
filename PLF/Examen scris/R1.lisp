(defun F(L)
	(max (car L) (caddr L))
	)

(setq F 10)

(setq G 'F)

(defun inlocuiree(k l e)
	(cond 
		((and (= k 0) (atom l)) (list e))
		((atom l) (list l))
		(t (list(apply #'append (mapcar #'(lambda (v)
							(inlocuiree (- k 1) v e)
							)
							l)
			)
		)
		)
	)
)

(defun inloc (L k e)
	(car (inlocuiree  k L e))
	)

(defun existaNr(L)
	(cond
		((null L)nil)
		((numberp (car L)) T)
		((atom (car L)) (existaNr (cdr L)))
		((listp (car L)) (or (existaNr (car L)) (existaNr (cdr L))))
		)
	)

(defun numar(L)
	(cond
		((null L) nil)
		((numberp (car L)) (car L))
		((and (listp (car L))(existaNr (Car L))) (numar (car L)))
		(T (numar (cdr L)))
		)
	)

(defun nrSub(L)
	(cond
		((atom L) 0)
		((and (existaNr L) (= (mod (numar L) 2) 0)) 
			(+ 1 (apply #'+ (mapcar #'nrSub L)))
			)
		(T (apply #'+ (mapcar #'nrSub L) ))
		
	)
	)