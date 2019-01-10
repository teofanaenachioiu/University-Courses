;; Un arbore binar se memoreaza
;; (radacina (lista-subarbore-1) (lista-subarbore-2)). Sa se 
;; determine lista nodurilor de pe nivelul k din arbore. Nivelul radacinii
;; se considera 0. De exemplu:
;; (A (B)(C)) 0 -> (A)

(defun noduri_aux (L N C)
	(cond
		((null L) nil)
		((= N C) (list (car L)))
		(T (append (noduri_aux (cadr L) N (+ C 1)) 
				 (noduri_aux (caddr L) N (+ C 1))))
		)
	)

(defun noduri (L N)
	(noduri_aux L N 0)
	)