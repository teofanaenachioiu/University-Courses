;; Dandu-se o lista, sa se construiasca lista primelor elemente ale tuturor 
;; elementelor lista ce au un numar impar de elemente la nivel superficial.
;; De exemplu:
;; (1 2 (3 (4 5 5) (6 7)) 8 (9 10 11)) => (1 3 4 9).

(defun elemente(L) 
	(cond
		((null L) nil)
		((and (listp (car L)) (= (mod (length (car L)) 2) 1)) 
			(append (elemente (car L))(cons (caar L) (elemente (cdr L))) ))
		((listp (car L)) (append (elemente (car L)) (elemente (cdr L))))
		(T (elemente (cdr L)))
	)
)

(defun elem(L)
	(cond
		((= (mod (length L) 2) 1) (cons (car L) (elemente L)))
		(T (elemente L)) 
		)
	)