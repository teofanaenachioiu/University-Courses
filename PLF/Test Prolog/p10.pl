%maxim(L,M,Rez)
maximAux([],M,M):-!.
maximAux([H|T],M,Rez):-H>M,!, maximAux(T,H,Rez).
maximAux([_|T],M,Rez):-maximAux(T,M,Rez).

maxim([],0):-!.
maxim([H|T],Max):-maximAux(T,H,Max).

eliminAux([],_,[]):-!.
eliminAux([H|T],E,[H|R]):-H\=E,!,eliminAux(T,E,R).
eliminAux([E|T],E,R):-eliminAux(T,E,R).

%elimin(L,R)
elimin(L,R):-maxim(L,Max),eliminAux(L,Max,R).
