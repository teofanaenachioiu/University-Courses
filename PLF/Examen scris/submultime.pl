candidat(E,[E|_]).
candidat(E,[_|T]):-candidat(E,T).

apare(E,[E|_]):-!.
apare(E,[_|T]):-apare(E,T).


submul(L,R):-candidat(E,L),aux(L,[E],R).

aux(_,R,R).
aux(L,[H|Col],R):-
    candidat(E,L),
    not(apare(E,[H|Col])),
    E<H,
    aux(L,[E,H|Col],R).
