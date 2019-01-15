(defun dublare(L)
	(cond
		((numberp L) (* 2 L))
		((atom L) L)
		(T (mapcar #'dublare L))
		)
	)