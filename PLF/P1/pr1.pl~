apare(E,[H|T]):-H=\=E,apare(E,T).
apare(E,[H|_]):-H=:=E.

diferenta([],_,[]):-!.
diferenta([H|T],L,R):-apare(H,L),!,diferenta(T,L,R).
diferenta([H|T],L,[H|R]):-not(apare(H,L)),diferenta(T,L,R).

ePar(E):-R is mod(E,2),R=:=0.


adaugaUnu([],[]):-!.
adaugaUnu([H|T],[H|R]):-not(ePar(H)),!,adaugaUnu(T,R).
adaugaUnu([H|T],[H|R]):-adaugaUnu([1|T],R).
