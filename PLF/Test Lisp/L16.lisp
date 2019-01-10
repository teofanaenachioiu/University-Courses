;; Sa se scrie o functie care sa testeze daca o lista liniara formata 
;; din numere intregi are aspect de "munte" (o multime se spune ca are
;; aspect de "munte" daca elementele cresc pana la un moment dat, apoi
;; descresc). De exemplu: 
;; (10 18 29 17 11 10) -> T


(defun maxim (maxi L)
	(cond
		((null L) maxi)
		((< maxi (car L)) (maxim (car L) (cdr L)))
		(T (maxim maxi (cdr L)))
	)
)

(defun secvDescresc (curent L)
	(cond
		((null L) T)
		((> curent (car L)) (secvDescresc (car L) (cdr L)))
		(T nil)
		)
	)

(defun secvCresc (maxi curent L)
	(cond
		((null L) nil)
		((and (< curent (car L)) (< (car L) maxi)) (secvCresc maxi (car L) (cdr L)))
		((and (= (car L) maxi) (not (null (cdr L)))) (secvDescresc maxi (cdr L)))
		(T nil)
		)
	)

(defun munte(L)
	(cond
		((null L) nil)
		((< (length L) 3) nil)
		(T (secvCresc (maxim (car L) L) (car L) (cdr L))
			)
		)
)