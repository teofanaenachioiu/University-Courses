(defun G(L)
		(* (car l) (cadr l))
	)

(SETQ CAR 'G)

(defun fct()
	(CAR '(2 3 5 6))
	)

(defun nrNoduri(arb k)
	(cond
		((and (atom arb) (= k 0)) 1)
		((atom arb) 0)
		(T (apply #'+ (mapcar #'(lambda (l)(nrNoduri l (- k 1))) arb))
		)
	)
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

(defun nrImp(L)
	(cond
		((atom L) 0)
		((= (mod (numar L) 2) 1) (+ 1 (apply #'+ (mapcar #'nrImp L))))
		(T (apply #'+ (mapcar #'nrImp L)))
		)
	)