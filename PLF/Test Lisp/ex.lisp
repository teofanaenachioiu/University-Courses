(defun minim(mini L)
	(cond
		((null L) mini)
		((and (numberp (car L)) (< (car L) mini)) (minim (car L) (cdr L)))
		((listp (car L)) (min (minim mini (car L)) (minim mini (cdr L)) ))
		(T (minim mini (cdr L)))
		)
	)

(defun adaug (L E mini)
	(cond
		((null L) nil )
		((and (numberp (car L)) (equal (car L) mini))
			(append (list (car L) E) (adaug (cdr L) E mini)))
		((listp (car L)) 
			(append (list (adaug (car L) E mini)) (adaug (cdr L) E mini)))
		(T (append (list (car L)) (adaug (cdr L) E mini)))
		)
	)

(defun adaugare (L E)
	(cond
		((null L) nil)
		((and (numberp (car L))(null (cdr L))) (list (car L) E))
		;;((and (atom (car L)) (null (cdr L))) (car L))
		(T 
			(adaug L E (minim 2000 L)))
		)
	)