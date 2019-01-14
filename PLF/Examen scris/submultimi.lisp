(defun inserare(e L)
	(cond
		((null L) nil)
		(T (cons (cons e (car L)) (inserare e (cdr L))))
	)
)

(defun submultimi (L)
	(cond
		((null L) (list nil))
		(T (
			(lambda (V) 
				(append V (inserare (car L) V) ) 
				)
			(submultimi (cdr L))
			)
		)
	)
)

(defun pereche (E L)
	(cond
		((null L) nil)
		((< E (car L)) (cons (list E (car L)) (pereche E (cdr L))))
		(T (pereche E (cdr L)))
		)
	)

(defun perechi(L)
	(cond
		((null L) nil)
		(T (append (pereche (car L) (cdr L)) (perechi (cdr L))))
		)
	)

(defun insPoz(e l poz)
	(cond
		((= poz 1) (cons e l))
		(T (cons (car L) (insPoz e (cdr L) (- poz 1))))
		)
	)

(defun toatePoz(e l k)
	(cond
		((= k 0) nil)
		(T (cons (insPoz e l k) (toatePoz e l (- k 1))))
		)
	)

(defun ins( e l)
	(toatePoz e l (+ (length l) 1))
	)

;;cu mapcar
(defun perm(l)
	(cond
		((null l)  nil)
		(T(cons (ins (car l) (perm (cdr l)) ) nil)   ) 
		)
	)

(defun stanga (l nrv nrm)
	(cond
		((or (null l)(= nrv (+ nrm 1)) ) nil)
		(T (cons (car l) (cons (cadr l) (stanga (cddr l) (+ nrv 1) (+ nrm (cadr l))))))
		)
	)

(defun dreapta (l nrv nrm)
	(cond
		((null l) nil)
		((= nrv (+ nrm 1)) l)
		(T (dreapta (cddr l) (+ nrv 1) (+ nrm (cadr l))))
		)
	)

(defun creeazaArb (e)
	(list e nil nil)
	)

(defun add(e arb)
	(cond
		((null arb) (creeazaArb e))
		((< e (car arb)) 
			(cons (car arb) (cons (add e (cadr arb)) (list(caddr arb)))))
		((> e (car arb)) 
			(cons (car arb) (cons (cadr arb) (list (add e (caddr arb)))))
		)
	)
	)

(defun adaug(l arb)
	(cond 
		((null l) arb)
		(T (adaug (cdr l) (add (car l) arb)))
		)
	)

(defun inordine (arb)
	(cond
		((null arb) nil)
		(T (append (inordine (cadr arb)) (list (car arb)) (inordine (caddr arb))))
		)
	)

(defun sortare(l)
	(inordine (adaug l nil))
	)

(defun mapi()
(mapcon #'(lambda (L) (mapcon #'list L)) '(1 2 3))
	)

(defun nr(L)
	(cond
		((atom L) 1)
		(T (apply #'+ (mapcar #'nr L)))
		)
	)

(defun modif(L)
	(cond
		((numberp L) (* 2 L))
		((atom L) L)
		(T (mapcar #'modif L))
		)
	)

(defun nrApar(E L)
	(cond
		((equal E L) 1)
		((atom L) 0)
		(T (apply #'+ (mapcar #'(lambda (V) (nrApar E V)) L)))
		)
	)

(defun lgm(L)
	(cond
		((atom L) 0)
		(T(apply #'max (cons (length L) (mapcar #'lgm L))))
		)
	)

(defun eliminare (L)
	(cond
		((numberp L) nil)
		((atom L) (list L))
		(T (list (apply #'append (mapcar #'eliminare L))))

		)
	)

(defun adancime(arb)
	(cond
		((null (cdr arb)) 0)
		((atom arb) 0)
		(T (+ 1 (apply #'max (mapcar #'adancime (cdr arb)))))
		)
	)

(defun lista(L n)
	(cond
		((and (= n 0) (atom L)) (list L))
		((or (= n 0) (atom L)) nil)
		(T (mapcan #'(lambda (L) (lista L (- n 1))) L))
		)
	)

(defun m (L)
 	(cond
 		((numberp L) L)
		((atom L) most-negative-fixnum)
 		(t (apply #'max
				(mapcar #'m L)
 				)
 		)
 	)
)

(defun p (L)
 	(mapcan #'(lambda (e1)
 		(mapcar #'(lambda (e2)
 			(list e1 e2)
 		)
 		L)
 	)
 	L)
) 

(defun fct (L)
	(cond
 		((null (car L)) nil)
 		(t (cons
			(mapcar #'car L)
			(fct (mapcar #'cdr L))
 			)
 		)
 	)
)

(defun subm (L)
 	(cond
 		((null L) (list nil))
 		(t (
 			(lambda (s)
 			(append s (mapcar #'
 				(lambda (sb)
 				(cons (car L) sb)
 				)
 				s)
 			)
 			)
 		(subm (cdr L))
 		)
 	)
 )
 	)
(setq n 10)

(defmacro dec (n)
  `(setq ,n (- ,n 1))
)
(defun decrem(n)
	(dec n)
	n
	)

(defun gen(f L)
	(cond
		((null L) T)
		(T (and (funcall f (car L)) (gen f L)) )
		)
	)
(defun toate(L)
	(cond
	((null L)T)
	(t(gen #'(lambda (e) (gen 'atom e)) L))
	)
	)

(defun produs(L)
	(cond
		((numberp L) L)
		((atom L) 1)
		(T (apply #'*(mapcar #'produs L)))
		)
	)

(defun existaNr (L)
	(cond
		((null L) nil)
		((numberp (car L)) T)
		((listp (car L)) (or (existaNr (car L)) (existaNr (cdr L))))
		(T(existaNr (cdr L)))
		)
	)

(defun verificare(L)
	(cond
		((null L) nil)
		((and (numberp (car L))(= (car L) 5)) t)
		((and (listp (car L)) (existaNr (car L)) ) (verificare (car L)))
		((listp (car L)) (verificare (cdr L)))
		(T (verificare (cdr L)))
		)
	)

(defun numara(L)
	(cond
		((atom L) 0)
		((verificare L) (+ 1 (apply #'+ (mapcar #'numara L))))
		(T (apply #'+ (mapcar #'numara L)))
		)
	)