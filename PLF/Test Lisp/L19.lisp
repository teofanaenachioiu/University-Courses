;; Dandu-se o lista liniara, se cere sa se elimine elementele
;; din N in N. De exemplu:
;; (elimin '(1 2 3 4 5) '2) --> (1 3 5)

(defun elimina (L C N)
	(cond
		((null L) nil)
		((= C N) (elimina (cdr L) 1 N))
		(T (cons (car L) (elimina (cdr L) (+ C 1) N)) )
		)
	)

(defun elimin(L N)
	(elimina L 1 N)
	)