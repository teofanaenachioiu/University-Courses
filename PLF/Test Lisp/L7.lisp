;; Sa se scrie o functie care insereaza un element dat pe toate 
;; pozitiile intr-o  lista liniara si returneaza o lista de liste. 
;; De exemplu: 
;; (inserare '1 '(2 3)) --> ((1 2 3) (2 1 3) (2 3 1))

(defun inserare_aux (E Lf L) 
	(cond
		((null L) (list (append Lf (list E))))
		(T (cons (append Lf (cons E L)) 
			(inserare_aux E (append Lf (list(car L))) (cdr L))))
	)
)

(defun inserare (E L)
	(inserare_aux E nil L)
	)

