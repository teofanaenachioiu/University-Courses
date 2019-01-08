;; Dandu-se o lista liniara si un numar N sa se adauge in lista
;; un element pe pozitiile N, 2N, 4N, etc... De exemplu:
;; (adaug '(1 2 3 4 5 6) '2 '7) --> (1 2 7 3 4 7 5 6) 

(defun adaugare (L C N E)
	(cond 
		((null L) nil)
		((= C N) (append (list (car L) E) (adaugare (cdr L) (+ C 1) (* N 2) E)))
		(T (append (list (car L)) (adaugare (cdr L) (+ C 1) N E) ))
		)
	)

(defun adaug (L N E)
	(adaugare L 1 N E)
	)