produsAux([],Pp,Pp):-!.
produsAux([H|T],Pp,Pf):-Pp1 is Pp*H,produsAux(T,Pp1,Pf).

produs(L,R):-produsAux(L,1,R).

cmmdcAux(A,B,A):-B =:=0,!.
cmmdcAux(A,B,P):- Pp is mod(A,B),cmmdcAux(B,Pp,P).

cmmmcAux(A,B,R):-cmmdcAux(A,B,R2),R is div(A*B,R2).

cmmmc([],1):-!.
cmmmc([H1|T],Pp):-cmmmcAux(H1,Pp,P),cmmmc(T,P).
