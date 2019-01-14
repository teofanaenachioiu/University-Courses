putere(_,0,1):-!.
putere(X,N,R):-Rest is N mod 2, Rest=0, !, N1 is N div 2, putere(X,N1,R1), R is R1*R1.
putere(X,N,R):-N1 is N div 2, putere(X,N1,R1), R is R1*R1*X.

candidat(E,[E|_]).
candidat(E,[_|T]):-candidat(E,T).

aranjamente(L,K,S,R):-candidat(E,L),E=<S,aranj(L,K,S,[E],E,1,R).
apare(E,[E|_]):-!.
apare(E,[_|T]):-apare(E,T).

aranj(_,K,S,R,S,K,R):-!.
aranj(L,K,S,Col,Sp,Lg,R):-
    candidat(E,L),
    not(apare(E,Col)),
    S1 is Sp+E,
    S1=<S,
    Lg1 is Lg+1,
    Lg1=<K,
    aranj(L,K,S,[E|Col],S1,Lg1,R).
