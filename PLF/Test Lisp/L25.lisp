;; Un arbore binar se memoreaza
;; (radacina (lista-subarbore-1) (lista-subarbore-2)). 
;; Sa se decida daca un arbore este echilibrat (diferenta dintre adancimile
;; celor 2 subarbori nu este mai mare decat 1, la fiecare nod). De exemplu:
;; (A (B)(C)) -> T

(defun adancime (L)
	(cond
		((null L) 0)
		(T (+ 1 (max (adancime (cadr L)) (adancime (caddr L)))))
	)
)

(defun echilibrat (L)
	(cond 
		((null L) T)
		((= 1 (- (adancime (cadr L)) (adancime (caddr L)))) 
			(and  (echilibrat (cadr L)) (echilibrat (caddr L))))
		((= 0 (- (adancime (cadr L)) (adancime (caddr L)))) 
			(and  (echilibrat (cadr L)) (echilibrat (caddr L))))
		((= -1 (- (adancime (cadr L)) (adancime (caddr L)))) 
			(and  (echilibrat (cadr L)) (echilibrat (caddr L))))
		(T nil)
	)
)