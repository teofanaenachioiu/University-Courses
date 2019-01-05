;; Sa se inlocuiasca fiecare sublista a unei liste cu ultimul ei element. 
;; Prin sublista se intelege element de pe primul nivel, care este lista. 
;; De exemplu:
;; (a (b ((c))) (d (e (f)))) ==> (a c f)

(defun invers (L col)
	(cond 
		((null L) col)
		(T (invers (cdr L) (cons (car L) col)))
		)
	)


(defun ultim_aux(L)
	(cond
		((null L) nil)
		((listp (car L)) (ultim_aux (car L)))
		((> (length L) 1) (ultim_aux (cdr L)))
		(T (car L))
		)
	)

(defun ultim(L)
	(ultim_aux (invers L nil))
	)

(defun substitutie (L)
	(cond
		((null L) nil)
		((listp (car L)) (cons (ultim (car L)) (substitutie (cdr L)) ))
		(T (cons (car L) (substitutie (cdr L))))
		)
	)