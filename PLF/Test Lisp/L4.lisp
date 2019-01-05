;; Definiti o functie care, dintr-o lista liniara de atomi, produce 
;; o lista de  perechi (atom n), unde atom apare in lista initiala 
;; de n ori. De exemplu:  
;; (A B A B A C A) --> ((A 4) (B 2) (C 1))

(defun elimina (E L)
	(cond
	((null L) nil)
	((equal E (car L)) (elimina E (cdr L)))
	(T (cons (car L) (elimina E (cdr L))))
	)
)

(defun numara (E L)
	(cond
		((null L) 0)
		((equal E (car L)) ( + 1 (numara E (cdr L))))
		(T (numara E (cdr L)))
		)
	)

(defun frecventa (L)
	(cond
		((null L) nil)
		(T (cons (list (car L) (numara (car L) L)) (frecventa
			(elimina (car L) L))))
		)
	)