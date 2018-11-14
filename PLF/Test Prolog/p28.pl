primA(E,D,Nr,Nr):-D>E,!.
primA(E,D,Nr,Nrf):-R is mod(E,D), R=\=0,!,D1 is D+1,primA(E,D1,Nr,Nrf).
primA(E,D,Nr,Nrf):-mod(E,D)=:=0,D1 is D+1, Nr1 is Nr+1,primA(E,D1,Nr1,Nrf).
prim(E):-primA(E,1,0,N),N=2.

listaPrime([],[]):-!.
listaPrime([H|T],[H|R]):-prim(H),!,listaPrime(T,R).
listaPrime([_|T],R):-listaPrime(T,R).
