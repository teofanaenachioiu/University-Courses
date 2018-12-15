(defun suma (L)
	(cond 
		((and (numberp L) (= (mod L 2) 0)) 
			L)

		((and (numberp L) (= (mod L 2) 1)) 
			(* -1 L))
			
		((atom L) 0)
		
		(T (apply #'+ (mapcar #'suma L) ))

		)
	)