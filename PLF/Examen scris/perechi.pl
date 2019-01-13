pereche(A,[B|_],[A,B]):-A<B.
pereche(E,[_|T],P):-pereche(E,T,P).

perechi([H|T],P):-pereche(H,T,P).
perechi([_|T],P):-perechi(T,P).

toate(L,R):-findall(P,perechi(L,P),R).
