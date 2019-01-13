f([],-1).
f([H|T],S):-f(T,S1),S1>0,!,S is S1+H.
f([_|T],S):-f(T,S1),S is S1.

f1([],-1).
f1([H|T],S):-f1(T,S1),f_aux(S1,S,H).

f_aux(S1,S,H):-S1>0,!,S is S1+H.
f_aux(S1,S,_):-S is S1.

exista([E|_],E):-!.
exista([_|T],E):-exista(T,E).

multime([],[]):-!.
multime([H|T],[H|R]):-not(exista(T,H)),!,multime(T,R).
multime([_|T],R):-multime(T,R).

multimeCol([],Col,Col):-!.
multimeCol([H|T],Col,R):-not(exista(T,H)),!, multimeCol(T,[H|Col],R).
multimeCol([_|T],Col,R):-multimeCol(T,Col,R).

candidat(E,[E|_]).
candidat(E,[_|T]):-candidat(E,T).

aranjProdus(L,K,P,R):-
    candidat(E,L),E=<P,aranjProdus_aux(L,K,P,[E],1,E,R).

aranjProdus_aux(_,K,P,R,K,P,R).
aranjProdus_aux(L,K,P,Col,Lg,Pp,R):-
    candidat(E,L),
    E=<P,
    Lg<K,
    Lg1 is Lg+1,
    Prod is Pp*E,
    Prod=<P,
    aranjProdus_aux(L,K,P,[E|Col],Lg1,Prod,R).

toate(L,K,P,R):-findall(A,aranjProdus(L,K,P,A),R).
