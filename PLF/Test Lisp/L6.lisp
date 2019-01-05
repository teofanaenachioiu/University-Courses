;; Sa se construiasca multimea atomilor numerici ai unei liste. 
;; De exemplu:
;; (1 F (2 (1 3 E (2 4) 3) E 1) (1 4)) ==> (1 2 3 4)

(defun elimin_dup(L)
	(cond
		((null L) nil)
		(T (cons (car L) (elimin_dup (elimin (car L) L))))
		)
	)

(defun multime_aux (L)
	(cond
		((null L) nil)
		((numberp (car L)) (cons (car L) (multime_aux (cdr L))))
		((listp (car L)) (append (multime_aux (car L)) (multime_aux (cdr L))))
		(T (multime_aux (cdr L)))
	)
	)

(defun multime (L)
	(elimin_dup (multime_aux L))
	)