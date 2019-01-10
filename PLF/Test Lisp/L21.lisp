;; Sa se adauge dupa aparitiile elementului numeric minim dintr-o
;; lista neliniara o valoare e data. De exemplu:
;; (1 2 3 4 5) 11 -> (1 11 2 3 4 5)

(defun minim(mini L)
	(cond
		((null L) mini)
		((listp (car L)) (min (minim mini (cdr L)) (minim mini (car L) )))
		((and (numberp (car L))(< (car L) mini)) (minim (car L) (cdr L)))
		(T (minim mini (cdr L)))
		)
	)

(defun adaugare(L E mini)
	(cond
		((null L) nil)
		((and (numberP (car L)) (equal (car L) mini) 
			(append (list mini E) (adaugare (cdr L) E mini)) )) 
		((listp (car L)) 
			(append (list (adaugare (Car L) E mini)) (adaugare(Cdr L) E mini)))
		(T (append (list (car L)) (adaugare (cdr L) E mini)))
		)

	)

(defun adaug (L E)
	(adaugare L E (minim (car L) L))
	)

