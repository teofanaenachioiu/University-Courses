;; Sa se adauge dupa aparitiile elementului numeric minim dintr-o
;; lista neliniara o valoare e data. De exemplu:
;; (1 2 3 4 5) 11 -> (1 11 2 3 4 5)

(defun minim(mini L)
	(cond
		((null L) mini)
		((listp (car L)) (max (minim mini (cdr L)) (minim mini (car L) )))
		((and (numberp (car L))(< (car L) mini)) (minim (car L) (cdr L)))
		(T (minim mini (cdr L)))
		)
	)

(defun adaugare(L E)
	(cond
		((null L) nil)
		((and (numberP (car L)) (equal (minim (car L) L)) ) 
			(append (list )))
		)
	)

