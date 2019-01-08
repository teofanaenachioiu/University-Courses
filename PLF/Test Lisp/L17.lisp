;; Sa se elimine toate aparitiile elementului numeric maxim dintr-o 
;; lista  neliniara. De exemplu:
;; (1 2 3 4 5 6) -> (1 2 3 4 5)

(defun maxim (maxi L)
	(cond
		((null L) maxi)
		((listp (car L)) (max (maxim maxi (car L))  (maxim maxi (cdr L))))
		((> (car L) maxi) (maxim (car L) (cdr L)))
		(T (maxim maxi (cdr L)))
	)
)

(defun elimina (L)
	(cond
		((null L) nil)
		((listp (car L)) (cons (elimina (car L)) (elimina (cdr L))))
		((equal (maxim (car L) L) (car L)) (elimina (cdr L)))
		(T (cons (car L) (elimina (cdr L))))
		)
	)

