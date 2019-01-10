(defun permutari (L)
 	(cond
 		((null (cdr L)) (list L))
 		(t (mapcan #'(
 			lambda (e)
 			(mapcar #'(
 				lambda (p) (cons e p)
 				)
 				(permutari (remove e L))
 				)
 			)
 			L
 			)
 		)
 	)
)