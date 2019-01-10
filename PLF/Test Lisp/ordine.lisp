(defun preordine (arb)
	(cond
		((null arb) nil)
		(T(append (list (car arb)) (preordine (cadr arb )) (preordine (caddr arb))))
	)
)

(defun inordine(arb)
	(cond
		((null arb) nil)
		(T(append (inordine (cadr arb)) (list (car arb)) (inordine (caddr arb))))
		)
	)

