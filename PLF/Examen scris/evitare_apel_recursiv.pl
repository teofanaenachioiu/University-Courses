m([H],H).
m([H|T],H):-m(T,M),H=<M,!.
m([_|T],M):-m(T,M).

m1([H],H).
m1([H|T],M):-m1(T,M),H>M,!.
m1([H|_],H).

f(1,1):-!.
f(2,2):-!.
f(K,X):-K1 is K-1, f(K1,Y),Y>1,!,X is K-2.
f(K,X):-K1 is K-1, f(K1,X).

f1(1,1):-!.
f1(2,2):-!.
f1(K,X):-K1 is K-1, f(K1,Y),f_aux(Y,K,X).

f_aux(Y,K,X):-Y>1,!,X is K-2,!.
f_aux(_,X,X).
