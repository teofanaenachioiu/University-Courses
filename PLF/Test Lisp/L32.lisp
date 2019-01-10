;; Dandu-se o lista liniara, sa se stearga toate secventele de valori
;; numerice consecutive. De exemplu: 
;; (1 2 c 4 6 7 8 i 10 j) --> (c 4 i 10 j).

(defun eliminare (L flag)
	(cond
		((null L) nil)
		((and(null (cdr L)) (= flag 1)) nil)
		((null (cdr L)) (cons (car L) nil))
		((and (numberp (car L))(numberp (cadr L))) (eliminare (cdr L) 1))
		((and (numberp (car L)) (atom (cadr L)) (= flag 1)) (eliminare (cdr L) 1))
		(T (cons (car L) (eliminare (cdr L) 0)))
		)
	)