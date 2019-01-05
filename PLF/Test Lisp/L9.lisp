;; Definiti o functie care interclaseaza cu pastrarea dublurilor 
;; doua liste liniare sortate. De exemplu:
;; (1 2 3 4 5) (1 2 3 4 5) -> (1 1 2 2 3 3 4 4 5 5)

(defun interclasare (L K)
	(cond 
		((null L) K)
		((null K) L)
		((< (car L) (car K)) (cons (car L) (interclasare (cdr L) K)))
		(T (cons (car K) (interclasare L (cdr K))))
		)
	)