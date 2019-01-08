;; Se da o lista neliniara. Sa se stearga toti atomii numerici
;; care apar de un numar par de ori in lista. De exemplu: 
;; (sterg '(1 g (1 h 8 e (2)))) --> (g (h 8 e (2)))

(defun aparitii (E L nr)
	(cond
		((null L) nr)
		((equal E (car L)) (aparitii E (cdr L) (+ nr 1)))
		((listp(car L)) (+ (aparitii E (car L) 0) (aparitii E (cdr L) nr)) )
		(T (aparitii E (cdr L) nr))
		)
	)

(defun stergere(E L)
	(cond 
		((null L) nil)
		((equal E (car L)) (stergere E (cdr L)))
		((listp (car L)) (append (list(stergere E (car L))) (stergere E (cdr L))))
		(T (append (list (car L)) (stergere E (cdr L))))
		)
	)

(defun sterg(L)
	(cond 
		((null L) nil)
		((and (numberp (car L)) (= (mod (aparitii (car L) L 0) 2) 0)) 
			(sterg (stergere (car L) (cdr L)))
			)
		((listp (car L)) (append (list(sterg (car L))) (sterg (cdr L))))
		(T (append (list (car L)) (sterg (cdr L)))
		)
	)
)