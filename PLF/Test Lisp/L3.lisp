;; Sa se scrie o functie care, primind o lista liniara, intoarce multimea 
;; tuturor  perechilor de atomi nenumerici din lista. De exemplu: 
;; (A 2 B 3 C D 1)  --> ((A B) (A C) (A D) (B C) (B D) (C D))

(defun perechi_aux (E L)
	(cond
		((null L) nil)
		((numberp (car L)) (perechi_aux E (cdr L)))
		(T (cons (list E (car L)) (perechi_aux E (cdr L))))
	)
)

(defun perechi (L)
	(cond 
		((null L) nil)
		((numberp (car L)) (perechi (cdr L)))
		(T (append (perechi_aux (car L) (cdr L)) (perechi (cdr L)))
		)
	)
)
