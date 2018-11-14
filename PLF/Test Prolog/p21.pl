concatenare([],L,L):-!.
concatenare([H|T],L,[H|R]):-concatenare(T,L,R).

substitutie([],_,_,[]):-!.
substitutie([H|T],E,L,R):-H=E,!,substitutie(T,E,L,Rr), concatenare(L,Rr,R).
substitutie([H|T],E,L,[H|R]):-substitutie(T,E,L,R).
