;; Sa se scrie o functie care insereaza un element dat pe toate 
;; pozitiile intr-o  lista liniara si returneaza o lista de liste. 
;; De exemplu: 
;; (inserare '1 '(2 3)) --> ((1 2 3) (2 1 3) (2 3 1))

(defun inserare (E L) 
	(cond
		((null L) (list E))
		(T (append  (list E (car L)) (inserare E (cdr L)))
		)
	)
)