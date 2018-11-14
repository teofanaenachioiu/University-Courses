primA(E,D,Nr,Nr):-D>E,!.
primA(E,D,Nr,Nrf):-R is mod(E,D), R=\=0,!,D1 is D+1,primA(E,D1,Nr,Nrf).
primA(E,D,Nr,Nrf):-mod(E,D)=:=0,D1 is D+1, Nr1 is Nr+1,primA(E,D1,Nr1,Nrf).
prim(E):-primA(E,1,0,N),N=2.

elimin([],_,[]):-!.
elimin([_],1,[]):-!.
elimin([E],0,[E]):-!.
elimin([H1,H2|T],_,R):-
    prim(H1),prim(H2),!,
    elimin([H2|T],1,R).
elimin([H1,H2|T],0,[H1|R]):-
    not(prim(H1)),not(prim(H2)),!,
    elimin([H2|T],0,R).
elimin([H1,H2|T],1,R):-
    prim(H1),not(prim(H2)),!,
    elimin([H2|T],0,R).
elimin([H1,H2|T],0,[H1|R]):-
    elimin([H2|T],0,R).

eliminare(L,R):-elimin(L,0,R).

