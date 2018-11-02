factorial(N,F,N,F):-!.
factorial(N,F,I,P):-N=I,F is P,!.
factorial(N,F,I,P):-I1 is I+1,P1 is P*I1, factorial(N,F,I1,P1).

adaugaSf([],E,[E]):-!.
adaugaSf([H|T],E,[H|L]):-adaugaSf(T,E,L).
adaugaSf([H|T],E,Rez):-adaugaSf(T,E,L), Rez=[H|L].
