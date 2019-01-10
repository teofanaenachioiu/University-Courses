;; Sa se elimine toate aparitiile elementului numeric maxim dintr-o 
;; lista  neliniara. De exemplu:
;; (1 2 3 4 5 6) -> (1 2 3 4 5)

(defun maxim (maxi L)
	(cond
		((null L) maxi)
		((listp (car L)) (max (maxim maxi (car L))  (maxim maxi (cdr L))))
		((> (car L) maxi) (maxim (car L) (cdr L)))
		(T (maxim maxi (cdr L)))
	)
)

(defun elimin (L maxi)
(cond
    ((null L) nil)
    ((and (numberP (car L)) (= (car L) maxi)) (elimin(cdr L) maxi))
     ((and (listp (car L)) (null (elimin (car L) maxi))) (elimin (cdr L) maxi))
    ((listp (car L)) (append (list(elimin (car L) maxi)) (elimin (cdr L) maxi)))
    (T (append (list (car L)) (elimin (cdr L) maxi)))
))

(defun elimina (L)
    (elimin L (maxim L (car L)))
    )
