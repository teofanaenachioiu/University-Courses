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
%perechi(E:int,L:lista,LP:lista)
%E - primul element din seria de perechi
%L - lista de elemente cu care se acociaza elementul E
%LP - lista de perechi
%model de flux (i,i,o),(i,i,i)

perechi(_,[],[]).
perechi(E,[H|T],[[E|H]|LP]):-perechi(E,T,LP).

%determina lista de perechi
%listaAux(L:lista,LP:lista,LF:lista)
%L - lista de numere
%LP - lista partiala de perechi
%LF - lista finala de perechi
%model de flux (i,i,o), (i,i,i)
listaAux([],LP,LP).
listaAux([H|T],LP,LF):-perechi(H,T,L),append(LP,L,R),listaAux(T,R,LF).

%lista(L:lista,LF:lista)
%L - lista de numere
%LF - lista de perechi
%model de flux (i,o), (i,i)

lista(L,LF):-listaAux(L,[],LF).
