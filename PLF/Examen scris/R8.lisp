(DEFUN G(L)(+(CAR L)(CADR L)))
(SETQ H 'F)(SET H 'G)
(defun test()
	(F '(2 3 4 5 6 ))
	)

(defun test1()
	(funcall F '(2 3 4 5 6 ))
	)

(defun nr(L)
	(cond
		((numberp L) 1)
		((atom L) 0)
		(T (apply #'+ (mapcar #'nr L)))
		)
	)

(defun numara(L)
	(cond
		((atom L)0)
		((= (mod (nr L) 2) 1) (+ 1 (apply #'+ (mapcar #'numara L))))
		(T (apply #'+ (mapcar #'numara L)))
		)
	)

(defun elem(L)
	(* L 1)
	)

(defun num(L)
	(cond
		((atom L) 0)
		(T(apply #'+ (mapcar #'numara L)))
		)
	)

