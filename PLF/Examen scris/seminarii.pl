norocos(4):-!.
norocos(7):-!.
norocos(N):- N mod 10 =:=4,!, N1 is N div 10, norocos(N1).
norocos(N):- N mod 10 =:=7,!, N1 is N div 10, norocos(N1).

divizorN(N,S):-diviN(N,1,S).

diviN(N,N,N):-!.
diviN(N,I,S):-N mod I=:=0,!,I1 is I+1,diviN(N,I1,S1),S is S1+I.
diviN(N,I,S):-I1 is I+1,diviN(N,I1,S).

produs([],1):-!.
produs([H|T],P):-number(H),!,produs(T,P1),P is P1*H.
produs([_|T],P):-produs(T,P).

adauga(L,E,M,R):-adaug(L,E,M,1,R).

adaug([],E,M,M,[E]):-!.
adaug([],_,_,_,[]):-!.
adaug([H|T],E,M,M,R):-adaug(T,E,M,2,R1),!,R =[E,H|R1].
adaug([H|T],E,M,N,R):-N1 is N+1,adaug(T,E,M,N1,R1),R=[H|R1].

nrAp(_,[],0):-!.
nrAp(E,[E|T],N):-!,nrAp(E,T,N1),N is N1+1.
nrAp(E,[_|T],N):-nrAp(E,T,N).

elimin([],_,[]):-!.
elimin([H|T],Cop,R):-nrAp(H,Cop,NR),NR=1,!,elimin(T,Cop,R).
elimin([H|T],Cop,[H|R]):-elimin(T,Cop,R).

elimina(L,R):-elimin(L,L,R).

adaugaSf(E,[],[E]):-!.
adaugaSf(E,[H|T],[H|R]):-adaugaSf(E,T,R).

eliminCol([],_,Col,Col):-!.
eliminCol([H|T],Copie,Col,R):-nrAp(H,Copie,NR), NR=1,!,eliminCol(T,Copie,Col,R).
eliminCol([H|T],Copie,Col,R):-adaugaSf(H,Col,Col1),eliminCol(T,Copie,Col1,R).

eliminaCol(L,R):-eliminCol(L,L,[],R).

nrMunti([],0):-!.
nrMunti([H|T],N):-is_list(H),munte(H),!,nrMunti(T,N1),N is N1+1.
nrMunti([_|T],N):-nrMunti(T,N).

mun([_,_],1):-!.
mun([H1,H2,H3|T],V):-H1<H2,H2>H3,!, V1 is V+1, mun([H2,H3|T],V1).
mun([H1,H2,H3|T],V):-H1<H2,H2<H3,!, mun([H2,H3|T],V).
mun([H1,H2,H3|T],V):-H1>H2,H2>H3,!, mun([H2,H3|T],V).

munte([H1,H2,H3|T]):-mun([H1,H2,H3|T],0).

