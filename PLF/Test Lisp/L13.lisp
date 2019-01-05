;; Definiti o functie care substituie un element E prin elementele unei 
;; liste L1 la toate nivelurile unei liste date L. De exemplu:
;; (subst '2 '(3 4) '(1 (5 (2 7 (2))))) --> (1 (5 (3 4 7 (3 4))))

(defun substi (E S L)
	(cond
		((null L) nil)
		((equal E (car L)) (append S (substi E S (cdr L))))
		((listp (car L)) 
			(cons (substi E S (car L)) (substi E S (cdr L))))
		(T (cons (car L) (substi E S (cdr L))))
		)
	)