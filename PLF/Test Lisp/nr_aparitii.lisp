(defun elimin (L)
	(cond 
		((numberp L) nil)
		((atom L) (list L))
		(T (list (apply #'append (mapcar #'elimin L) )))
		)
	)