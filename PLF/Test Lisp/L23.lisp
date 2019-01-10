;; Un arbore binar se memoreaza 
;; (radacina (lista-subarbore-1) (lista-subarbore-2)).
;; Sa se afiseze calea de la radacina pana la un nod x dat. De exemplu:
;; (A (B) (C)) A -> (A)

(defun exista(E L)
	(cond
		((null L) nil)
		((listp (Car L)) (or (exista E (car L)) (exista E (cdr L))))
		((equal E (car L)) T)
		(T (exista E (cdr L)))
		)
	)

(defun cale(L E)
	(cond
		((null L) nil)
		((equal E (car L)) (list E))
		((exista E (cadr L)) (cons (car L) (cale (cadr L) E)))
		((exista E (caddr L)) (cons (car L) (cale (caddr L) E)))
		(T nil)
		)
	)

