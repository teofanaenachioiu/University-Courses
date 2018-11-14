concatenare([],L,L):-!.
concatenare([H|T],L,[H|Lf]):-concatenare(T,L,Lf).

substitutie([],_,_,[]):-!.
substitutie([H|T],E,L,R):-
    H=E,!,
    substitutie(T,E,L,R1),
    concatenare(L,R1,R).
substitutie([H|T],E,L,[H|R]):-
    substitutie(T,E,L,R).

elimina([],_,_,[]):-!.
elimina([_|T],Poz,N,R):-Poz=N,!, Poz1 is Poz+1,elimina(T,Poz1,N,R).
elimina([H|T],Poz,N,[H|R]):-Poz1 is Poz+1,elimina(T,Poz1,N,R).

elimin(L,N,R):-elimina(L,1,N,R).
