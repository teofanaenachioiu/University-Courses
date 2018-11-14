div(A,A,A):-!.
div(A,B,R):-A>B,!, A1 is A-B, div(A1,B,R).
div(A,B,R):-B1 is B-A, div(A,B1,R).

mul(A,B,M):-div(A,B,D),M is A*B//D.

cmmmcAux([],D,D).
cmmmcAux([H|T],D,Rez):-H1 is abs(H),mul(H1,D,D1),cmmmcAux(T,D1,Rez).

cmmmc([H|T],Rez):-H1 is abs(H),cmmmcAux(T,H1,Rez).












