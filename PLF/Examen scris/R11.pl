inserare(E,L,[E|L]):-check(E,L).
inserare(E,[H|T],[H|L]):- inserare(E,T,L),check(E,L).

permutari([],[]).
permutari([H|T],P):-permutari(T,P1),inserare(H,P1,P).

verif([],_):-!.
verif([_],_):-!.
verif([H1,H2|T],R):-Rez is abs(H1-H2),Rez=<3,verif([H2|T],R).


check(_,[]).
check(X,[H|_]) :-
    D is X - H,
    D < 4,
    D > -4.


candidat(E,[E|_]).
candidat(E,[_|T]):-candidat(E,T).

apare(E,[E|_]):-!.
apare(E,[_|T]):-apare(E,T).

lung([],0):-!.
lung([_|T],Nr):-lung(T,Nr1), Nr is Nr1+1.

perm(L,R):-candidat(E,L),lung(L,Nr),aux(L,Nr,[E],1,R).

aux(_,Nr,R,Nr,R):-!.
aux(L,Nr,[H|Col],K,R):-
    candidat(E,L),
    not(apare(E,[H|Col])),
    Rez is abs(H-E),
    Rez=<3,
    K1 is K+1,
    K1=<Nr,
    aux(L,Nr,[E,H|Col],K1,R).

