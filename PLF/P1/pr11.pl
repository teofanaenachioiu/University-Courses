suma([],_,S,S):-!.
suma([H|T],Nr,S,Sf):-
    Rest is mod(Nr,2),
    Rest =0,!,
    S1 is S-H,
    Nr1 is Nr+1,
    suma(T,Nr1,S1,Sf).
suma([H|T],Nr,S,Sf):-
    S1 is S+H,
    Nr1 is Nr+1,
    suma(T,Nr1,S1,Sf).

sumaAlter([],0):-!.
sumaAlter([H|T],S):-suma(T,2,H,S).
