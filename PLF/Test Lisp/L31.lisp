;; Definiti o functie care determina produsul unui numar reprezentat 
;; cifra cu cifra intr-o lista cu o anumita cifra. Obs: numerele sunt
;; mari (nu se pot converti in baza 10). De exemplu: 
;; (1 9 3 5 9 9) * 2   --> (3 8 7 1 9 8) 

(defun invers (L col)
	(cond
		((null L) col)
		(T (invers (cdr L) (append (list (car L)) col)))
		)
	)

(defun inmul (L C transp)
	(cond
		((and (null L) (= transp 0)) nil)
		((null L) (list transp))
		(T (append (list (mod (+ (* (car L) C) transp) 10)) 
			(inmul (cdr L) C (floor (+ (* (car L) C) transp) 10)  )))
		)
	)

(defun inmultire (L C)
	(invers (inmul (invers L nil) C 0) nil)
	)