;; Sa se stearga dintr-o lista neliniara toate sublistele (liniare)
;; care au numar par de elemente. De exemplu:      
;; (sterg  '((2 3 4) (6 (7 8) ((7 9) 8))) (6 8) 9)) --> ((2 3 4) (6) 9)

(defun sterg (L)
	(cond
		((null L) nil)
		((and (listp (car L)) (= (mod (length (car L)) 2) 0)) 
			(sterg (cdr L))
			)
		((listp (car L)) (append (list (sterg (car L))) (sterg (Cdr L)) ))
		(T (append (list (car L)) (sterg (cdr L))))
	)
)