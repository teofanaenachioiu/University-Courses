%determina daca elementul E apare in lista
%apartine(L:lista, E:int)
%L - lista de numere
%E - elementul de cautat
%model de flux (i,i)

apartine([],E):-fail.
apartine([H|T],E):-H=:=E.
apartine([H|T],E):-H=\=E,apartine(T,E).


%determina reuniunea a doua multimi
%reuniune(L1:multime,L2:multime,LR:multime)
%L1,L2 - multimile de reunit
%LR - reuniunea multimilor
%model de flux (i,i,o), (i,i,i)

reuniune([],L2,L2).
reuniune([H|T],L2,LR):-apartine(L2,H),reuniune(T,L2,LR).
reuniune([H|T],L2,[H|LR]):-not(apartine(L2,H)),reuniune(T,L2,LR).

%determina multimea tutuor perechilor din lista
