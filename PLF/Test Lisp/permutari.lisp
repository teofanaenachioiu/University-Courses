
(defun ins (E Lf L)
	(cond
		((null L) (list (append Lf (list E))))
		(T (cons (append Lf (list E) L) 
			(ins E (append Lf (list (car L))) (cdr L))))
		)
	)

(defun insereaza (E L)
	(ins E nil L)
)


(defun permutarile(Left L)
	(cond
		((null L) nil)
		(T 
			(append 
			 (append Left (insereaza (car L) (cdr L)))
				(permutarile (cons (car L) Left) (cdr L))
				)
			)
		)
	)



;;;;;;;;;;;;;;;;;;

(defun permutari(L)
	(cond
		((null L) nil)
		(T (append (insereaza (car L) (cdr L)) (permutari (cdr L))))
		)
	)

(defun buna (l)
  	(cond
  		((null l) nil)
  		(t (append (permutari (car l))(buna(cdr l))))
  	)
)
(defun bb (l )
	(buna( permutari l)))

(defun nrAparitiiN( e l)
	(cond
		((null l) 0)
		((equal (car l) e) (+ 1 (nrAparitiiN e (cdr l))))
		( t (nrAparitiiN e (cdr l)))
	)
)

(defun sterge ( e l)
	(cond
		((null l) nil)
		((equal (car l) e) (sterge e (cdr l)))
		(t (append (list (Car l)) (sterge e(cdr l))))
	)
)

(defun multime(l)
	(cond
		((null l)nil)
		((> (nrAparitiiN (car l) l)1) (append (list(car l)) (multime(sterge (car l) (cdr l)))))
		(t (append (list(car l)) (multime (cdr l))))
	)
)


