;; Sa se scrie o functie care sa testeze daca o lista liniara formata
;; din numere  intregi are aspect de "vale"(o multime se spune ca are
;; aspect de "vale" daca elementele descresc pana la un moment dat,
;; apoi cresc). De exemplu: 
;; (10 8 6 17 19 20) -> T

(defun minim (mini L)
	(cond
		((null L) mini)
		((> mini (car L)) (minim (car L) (cdr L)))
		(T (minim mini (cdr L)))
	)
)

(defun secvCresc (curent L)
	(cond
		((null L) T)
		((< curent (car L)) (secvCresc (car L) (cdr L)))
		(T nil)
		)
	)

(defun secvDescresc (mini curent L)
	(cond
		((null L) T)
		((and (> curent (car L)) (> (car L) mini)) (secvDescresc mini (car L) (cdr L)))
		((and (= (car L) mini) (not (null (cdr L)))) (secvCresc mini (cdr L)))
		(T nil)
		)
	)

(defun vale(L)
	(cond
		((null L) nil)
		((< (length L) 3) nil)
		(T (secvDescresc (minim (car L) L) (car L) (cdr L))
			)
		)
)