%(L:list of numbers,L:list of number)
%(i,o)-nondeterm

sP([],[]).
sP([_|T],S):-sP(T,S).
sP([H|T],[H|S]):-
    H mod 2 =:=0,
    !,
    sP(T,S).
sP([H|T],[H|S]):-sI(T,S).


%(L:list of numbers,L:list of number)
%(i,o)-nondeterm
sI([H],[H]):-
    H mod 2 =\=0,
    !.
sI([_|T],S):-
    sI(T,S).
sI([H|T],[H|S]):-
    H mod 2 =:=0,
    !,
    sI(T,S).
sI([H|T],[H|S]):-
    sP(T,S).
