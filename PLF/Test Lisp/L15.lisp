;; Definiti o functie care intoarce cel mai mic multiplu comun al 
;; atomilor numerici dintr-o lista neliniara. De exemplu:
;; (12 2 4 6 8) -> 24

(defun cmmdc(a b)
	(cond
		((= a b) a)
		((> a b) (cmmdc (- a b) b))
		(T (cmmdc a (- b a)))
		)
	)

(defun cmmmc2(a b)
	(floor (* a b) (cmmdc a b))
	)

(defun cmmmcAux (curent L)
	(cond
		((null L) curent)
		(T (cmmmcAux (cmmmc2 curent (car L)) (cdr L))
		)
	)
)

(defun cmmmc(L)
	(cond
		((null L) 0)
		((null (cdr L)) (car L))
		(T (cmmmcAux (car L) L))
		)
	)