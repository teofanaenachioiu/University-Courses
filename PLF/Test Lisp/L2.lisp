;; Sa se scrie o functie care plecand de la o lista data ca argument,
;; inverseaza numai secventele continue de atomi. De exemplu:
;; (A B C (D (E F) G H I)) ==> (C B A (D (F E) I H G))

(defun inversare (L)
	(inv_aux L nil)
)

(defun inv_aux  (L col)
	(cond
		((null L) col)
		((listp (car L)) (append col (list (inv_aux (car L) nil)) 
				(inv_aux (cdr L) nil)))
		(T (inv_aux (cdr L) (cons (car L) col)))
		)
	)