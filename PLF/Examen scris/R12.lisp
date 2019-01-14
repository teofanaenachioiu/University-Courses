(defun eNull(L)
	(cond
		((null L) T)
		((null (car L)) nil)
		(T (eNull (cdr L))
		)
	)
	)

(defun verif(L)
	(cond
		((numberp L) (list T))
		((atom L) (list nil))
		(T (mapcan #'verif L))
		)
	)

(defun eNumar(L)
	(eNull (verif L))
	)

(defun nrSub(L)
	(cond
		((atom L) 0)
		((eNumar L) (+ 1 (apply #'+ (mapcar #'nrSub L))))
		(T (apply #'+ (mapcar #'nrSub L)))
		)
	)