div(A,A,A):-!.
div(A,B,D):-A>B,!, A1 is A-B, div(A1,B,D).
div(A,B,D):-B1 is B-A, div(A,B1,D).

mul(A,B,P):-P is A*B.

cmmmcAux(A,B,M):-div(A,B,D),mul(A,B,P),M is P/D.

cmmmcA([],M,M):-!.
cmmmcA([H|T],M,Mf):-cmmmcAux(H,M,M1), cmmmcA(T,M1,Mf).

cmmmc([],1):-!.
cmmmc([H|T],M):-cmmmcA(T,H,M).

adaug([],_,_,_,[]):-!.
adaug([H|T],V,Poz,Nr,[H,V|R]):-
    Poz=Nr,!,Poz1 is Poz+1,Nr1 is Nr*2, adaug(T,V,Poz1,Nr1,R).
adaug([H|T],V,Poz,Nr,[H|R]):-Poz1 is Poz+1,adaug(T,V,Poz1,Nr,R).

adaugare(L,V,R):-adaug(L,V,1,1,R).
