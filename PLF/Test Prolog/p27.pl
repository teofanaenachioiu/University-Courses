maximAux([],M,M):-!.
maximAux([H|T],M,Max):-H>M,!,maximAux(T,H,Max).
maximAux([_|T],M,Max):-maximAux(T,M,Max).

maxim([],[]):-!.
maxim([H|T],M):-maximAux([H|T],H,M).

subAux([],_,_,[]):-!.
subAux([H|T],E,Max,[E|R]):-H=Max,!,subAux(T,E,Max,R).
subAux([H|T],E,Max,[H|R]):-subAux(T,E,Max,R).

substitutie(L,E,R):-maxim(L,Max),subAux(L,E,Max,R).
