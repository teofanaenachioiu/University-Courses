%elimin(L,E,Rez)

elimina([],_,_,[]):-!.
elimina([H|T],E,Nr,Rez):-
    H=E,
    Nr>0,!,
    Nr1 is Nr-1,
    elimina(T,E,Nr1,Rez).
elimina([H|T],E,Nr,[H|Rez]):-
    H\=E,
    Nr>0,!,
    elimina(T,E,Nr,Rez).
elimina([H|T],E,Nr,[H|Rez]):-
    H=E,
    Nr<1,!,
    elimina(T,E,Nr,Rez).

elimin(L,E,R):-elimina(L,E,3,R).





