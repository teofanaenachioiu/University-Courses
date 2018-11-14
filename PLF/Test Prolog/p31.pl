concatenare([],L,L):-!.
concatenare([H|T],L,[H|Lf]):-concatenare(T,L,Lf).

%prim(N,D,Nr,Nrf,L,Lf)
prim(N,D,Nr,Nr,L,L):-D>N,!.
prim(N,D,Nr,Nrf,L,Lf):-
    Rest is mod(N,D),
    Rest=0,!,
    Nr1 is Nr+1,
    D1 is D+1,
    L1=[D|L],
    prim(N,D1,Nr1,Nrf,L1,Lf).
prim(N,D,Nr,Nrf,L,Lf):-
    D1 is D+1,
    prim(N,D1,Nr,Nrf,L,Lf).

invers([],Col,Col):-!.
invers([H|T],Col,R):-Coll=[H|Col],invers(T,Coll,R).

adaug([],[]):-!.
adaug([H|T],R):-
    prim(H,1,0,Nr,[],Ldiv),
    Nr=\=2,
    H=\=1,!,
    invers(Ldiv,[],LInv),
    adaug(T,R1),
    concatenare(LInv,R1,R11),
    R=[H|R11].
adaug([H|T],[H|R]):-adaug(T,R).



















