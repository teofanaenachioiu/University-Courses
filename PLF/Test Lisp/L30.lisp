;; Se da o lista neliniara. Se cere sa se elimine prima aparitie
;; a unui element e din lista. De exemplu: 
;; (elimin '1 '(2 3 (1 (4 5)) 1)) --> (2 3 ((4 5)) 1)

(defun elimin(E L)
	(cond 
		((null L) nil)
		((equal E (car L)) (cdr L))
		((listp (car L)) (append (list(elimin E (car L))) (elimin E (cdr L))))
		(T (append (list (car L)) (elimin E (cdr L))))
		)
	)