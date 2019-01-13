adaugaSf([],E,[E]).
adaugaSf([H|T],E,Rez):-adaugaSf(T,E,L), Rez=[H|L].

invers([],[]).
invers([H|T],R):-invers(T,R1),adaugaSf(R1,H,R).
