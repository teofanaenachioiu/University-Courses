;; Un arbore binar se memoreaza 
;; (radacina (lista-subarbore-1) (lista-subarbore-2))
;; Sa se precizeze nivelul pe care apare un nod x in arbore. 
;; Nivelul radacinii se considera  a fi 0. De exemplu:  
;; (A (B)(C)) A -> 0

(defun nivel(L E)
	(nivel_aux L E 0)
	)

(defun nivel_aux (L E N)
	(cond
		((null L) -1)
		((equal E (car L)) N)
		(T (max (nivel_aux (cadr L) E (+ N 1)) (nivel_aux (caddr L) E (+ N 1))))
		)
	)