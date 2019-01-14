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
