;; Dandu-se o lista neliniara, se cere sa se elimine din lista toate
;; elementele care se repeta. De exemplu: 
;; (1 (2 c f 1 (d 2 c 4) e)) --> ((f (d 4) e))

(defun nrAparitii (E L)
	(cond 
		((null L) 0)
		((equal E (Car L)) (+ 1 (nrAparitii E (cdr L))))
		((listp (car L)) (+ (nrAparitii E (car L)) (nrAparitii E (cdr L))))
		(T (nrAparitii E (Cdr L)))
		)
	)

(defun deleted(E L)
	(cond 
		((null L) nil)
		((equal E (Car L)) (deleted E (cdr L)))
		((listp (car L)) (cons (deleted E (car L)) (deleted E (cdr L))))
		(T (cons (car L) (deleted E (Cdr L))))
	)
)

(defun eliminare (L)
	(cond
		((null L)nil)
		((listp (car L)) (cons (eliminare (car L)) (eliminare (cdr L))))
		((> (nrAparitii (car L) L) 1) (eliminare (deleted (car L )(cdr L))))
		(T (cons (car L) (eliminare (cdr L))))
		)
	)