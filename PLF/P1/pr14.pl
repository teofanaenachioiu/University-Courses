apare([E|_],E):-!.
apare([_|T],E):-apare(T,E).

egaleA([],_):-!.
egaleA([H|T],L):-apare(L,H),egaleA(T,L).

egale(L1,L2):-egaleA(L1,L2),egaleA(L2,L1).

selectA([],_,_,[]):-!.
selectA([H|_],Nr,Poz,E):-Nr=Poz,!,E is H.
selectA([_|T],Nr,Poz,E):-Nr1 is Nr+1,selectA(T,Nr1,Poz,E).

select(L,Poz,E):-selectA(L,1,Poz,E).
