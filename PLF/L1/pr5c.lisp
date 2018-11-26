;;; Definiti o functie care determina 
;;; suma a doua numere in reprezentare de
;;; lista si calculeaza numarul zecimal 
;;; corespunzator sumei.

(defun invers (L)
	(invers_aux L NIL)
	)

(defun invers_aux (L Col)
	(COND
		((NULL L) Col)
		(T (invers_aux (CDR L)(CONS (CAR L) Col)))
		)
	)

(defun suma_aux (L1 L2 transp)
	(cond
		(
			(AND (NULL L1) (NULL L2) (= transp 0))
			NIL
			)
		(
			(AND (NULL L1) (NULL L2) (= transp 1))
			(CONS 1 NIL)
			)
		(
			(NULL L2)
			(APPEND (CONS (MOD (+ (CAR L1) transp) 10) NIL) 
 			(suma_aux (CDR L1) NIL (floor (+ (CAR L1) transp) 10)))
		)
		(
			(NULL L1)
			(APPEND (CONS (MOD (+ (CAR L2) transp) 10) NIL) 
 			(suma_aux NIL (CDR L2) (floor (+ (CAR L2) transp) 10)))
		)
		(
			T
			(APPEND 
				(CONS (MOD (+ (CAR L2) (CAR L1) transp) 10) NIL) 
 			(suma_aux (CDR L1) (CDR L2) (floor (+ (CAR L1) (CAR L2) transp) 10)))
		)
	)
)

(defun suma (L1 L2)
	(invers (suma_aux (invers L1) (invers L2) 0)
		)
)

(defun sumaNrAux (L1 L2 transp deMul)
	(cond
		(
			(AND (NULL L1) (NULL L2) (= transp 0))
			0
			)
		(
			(AND (NULL L1) (NULL L2) (= transp 1))
			deMul
			)
		(
			(NULL L2)
			(+ 
				(* deMul (MOD (+ (CAR L1) transp) 10))
				(sumaNrAux 
					(CDR L1)
					NIL 
					(floor (+ (CAR L1) transp) 10)
					(* deMul 10)
				)
				
				)
			)
		(
			(NULL L1)
			(+
				(* deMul (MOD (+ (CAR L2) transp) 10))
				(sumaNrAux 
					NIL 
					(CDR L2) 
					(floor (+ (CAR L2) transp) 10)
					(* deMul 10)
					)
				)
				
			)
		(
			T
			(+
				(* deMul (MOD (+ (CAR L1)(CAR L2) transp) 10))
				(sumaNrAux 
					(CDR L1) 
					(CDR L2) 
					(floor (+ (CAR L1) (CAR L2) transp) 10)
					(* deMul 10)
					)
					
			)
		)
	)
	)

(defun sumaNr (L1 L2)
	(sumaNrAux (invers L1) (invers L2) 0 1) 
	)
