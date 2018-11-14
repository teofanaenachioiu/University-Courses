%pereche(E,L,Rez)
pereche(_,[],[]):-!.
pereche(E,[H|T],Rez):-pereche(E,T,Rez1),Rez=[[E, H]|Rez1].

concatenare([],L,L):-!.
concatenare([H|T],L,[H|Rez]):-concatenare(T,L,Rez).

multime([],[]):-!.
multime([H|T],Rez):-pereche(H,T,R),multime(T,Rez1), concatenare(R,Rez1,Rez).
