concatenare([],L,L):-!.
concatenare([H|T],L,[H|R]):-concatenare(T,L,R).

%substitutie(L,E,S,R)
substitutie([],_,_,[]):-!.
substitutie([H|T],E,S,[H|R]):-H\=E,!,substitutie(T,E,S,R).
substitutie([H|T],E,S,R):-H=E,substitutie(T,E,S,R1),concatenare(S,R1,R).
