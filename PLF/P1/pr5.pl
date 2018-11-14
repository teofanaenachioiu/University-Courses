sterg([],_,[]):-!.
sterg([H|T],H,R):-sterg(T,H,R),!.
sterg([H|T],E,[H|R]):-sterg(T,E,R).

numarare([],_,Nr,Nr):-!.
numarare([E|T],E,Nr,Nrf):-Nr1 is Nr+1,numarare(T,E,Nr1,Nrf),!.
numarare([_|T],E,Nr,Nrf):-numarare(T,E,Nr,Nrf).


lista([],[]):-!.
lista([H|T],R):-numarare(T,H,1,Nr),Item=[H,Nr],sterg(T,H,Rez),
    lista(Rez,R1),R=[Item|R1].
