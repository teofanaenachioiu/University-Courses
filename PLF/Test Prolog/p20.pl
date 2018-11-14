maxim([],M,M):-!.
maxim([H|T],M,R):-H>M,!,maxim(T,H,R).
maxim([_|T],M,R):-maxim(T,M,R).

pozAux(_,[],_,[]):-!.
pozAux([Ha|Ta],[H|T],Nr,[Nr|R]):-
    maxim([Ha|Ta],Ha,M),
    M=H,!,
    Nr1 is Nr+1,
    pozAux([Ha|Ta],T,Nr1,R).
pozAux([Ha|Ta],[_|T],Nr,R):-Nr1 is Nr+1, pozAux([Ha|Ta],T,Nr1,R).

pozMax(L,R):-pozAux(L,L,1,R).
