;;; Definiti o functie care intoarce 
;;; cel mai mare divizor comun al numerelor
;;; dintr-o lista liniara.


(defun cmmdcNr (a b)
	(cond
		((> a b) (cmmdcNr (- a b) b))
		((< a b)(cmmdcNr a (- b a)))
		(T a)
		)
	)

(defun cmmdc_aux (divizor L)
	(cond
		((NULL L) divizor)
		(T (cmmdc_aux (cmmdcNr divizor (CAR L)) (CDR L))
			)

		)
	)

(defun cmmdc (L)
	(cmmdc_aux (CAR L) L)
	)