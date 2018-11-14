%sa se calculeze suma elementelor unei liste

%sumaAux(L:lista,S:int,SF:int)
%L - lista cu numere
%S - suma partiala a numerelor
%SF - suma numerelor din lista
%modelul de flux (i,i,o), (i,i,i)

sumaAux([],S,S).
sumaAux([H|T],S,SF):-S1 is S+H, sumaAux(T,S1,SF).

%suma(L:lista,SF:int)
%L - lista cu numere
%SF - suma numerelor din lista
%modelul de flux (i,o), (i,i)

suma(L,SF):-sumaAux(L,0,SF).