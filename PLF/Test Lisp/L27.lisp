;; Sa se scrie o functie care sterge 1-ul, al 2-lea, al 4-lea, 
;; al 8-lea ...element al unei liste liniare. De exemplu:
;; (sterg '(1 2 3 4 5 6)) --> (3 5 6)

(defun stergere (L C N)
	(cond
		((null L) nil)
		((= C N) (stergere (cdr L) (+ C 1) (* N 2)))
		(T (cons (car L) (stergere (cdr L) (+ C 1) N)))
		)
	)

(defun sterg (L)
	(stergere L 1 1) 
	)