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
		((NUMBERP (CAR L)) (cmmdc_aux (cmmdcNr divizor (CAR L)) (CDR L))
			)
		(T (cmmdc_aux divizor (CDR L)))
		)
	)

(defun getNumar (L nr)
	(cond 
		((NULL L) nr)
		((NUMBERP (car L)) (car L))
		;((LISTP (car L)) (getNumar (cdr L) (getNumar (car L) 1))) 
		(T (getNumar (CDR L) nr))
		)
	)

(defun cmmdc (L)
	(cmmdc_aux (getNumar L 1) L)
	)