adaugaSf([],E,[E]):-!.
adaugaSf([H|T],E,[H|Rez]):-adaugaSf(T,E,Rez).

creare(0,[]):-!.
creare(N,Rez):-N1 is N-1, creare(N1,Rez1), adaugaSf(Rez1,N,Rez).

creare1(I,N,[]):-I>N,!.
creare1(I,N,[I|Rez]):-I1 is I+1,creare1(I1,N,Rez).

apartenenta([H|_],H):-!.
apartenenta([_|T],E):-apartenenta(T,E).

sterg(_,[],[]):-!.
sterg(E,[E|T],Rez):-sterg(E,T,Rez),!.
sterg(E,[H|T],[H|Rez]):-sterg(E,T,Rez).

elementPoz([],_,[]):-!.
elementPoz([H|_],1,H):-!.
elementPoz([_|T],K,N):-K1 is K-1, elementPoz(T,K1,N).

cautare(E,[E|_]):-!.
cautare(E,[_|T]):-cautare(E,T).

eMultime([]):-!.
eMultime([H|T]):-not(cautare(H,T)),eMultime(T).

multime([],[]):-!.
multime([H|T],[H|Rez]):-not(cautare(H,T)),!,multime(T,Rez).
multime([H|T],Rez):-cautare(H,T), multime(T,Rez).

inversare([],[]):-!.
inversare([H|T],Rez):-inversare(T,Rez1),adaugaSf(Rez1,H,Rez).

inversAux([],Col,Col):-!.
inversAux([H|T],Col,Rez):-Col1 = [H|Col],inversAux(T,Col1,Rez).

invers(L,R):-inversAux(L,[],R).
