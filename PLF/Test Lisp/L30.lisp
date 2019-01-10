;; Se da o lista neliniara. Se cere sa se elimine prima aparitie
;; a unui element e din lista. De exemplu: 
;; (elimin '1 '(2 3 (1 (4 5)) 1)) --> (2 3 ((4 5)) 1)

(defun exista(E L)
	(cond 
		((null L) nil)
		((equal E (car L)) T)
		((listp (car L)) (or (exista E (car L))(exista (cdr L))))
		(T (exista E (cdr L)))
		)
	)

(defun elimin(E L)
	(cond 
		((null L) nil)
		((equal E (car L) ) (elimin E (cdr L)))
		((and (listp (car L))(exista E (car L))) 
			(append (list (elimin E (car L))) (cdr L) ))
		((listp (car L)) 
			(append (list (Car L)) (elimin E (cdr L))))
		(T (append (list (car L)) (elimin E (cdr L)) ))
		)
	)