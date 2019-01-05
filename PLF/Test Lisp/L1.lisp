;; Definiti o functie care obtine dintr-o lista data, lista tuturor 
;; atomilor nenumerici care apar, pe  orice nivel, dar in ordine 
;; inversa. De exemplu:
;; (((A B) 2 C) 3 (D 1 E)) --> (E D C B A)

(defun nenum(L) 
	(cond
		((null L) nil) 
		((numberp (car L)) (nenum (cdr L)) )
		((listp (car L)) (append (nenum (cdr L)) (nenum (car L))) )
		(T (append (nenum (cdr L)) (list (car L))))
		)
	)