% Se dau N puncte in plan (prin coordonatele lor).
% Se cere sa se determinetoate submultimile de puncte coliniare.

coord([H|T],H,Y):-coord(T,H,Y),!.
coord([H|_],_,H):-!.

coliniare(J,K,L):-coord(J,X1,Y1),coord(K,X2,Y2),coord(L,X3,Y3),X1*Y2+X2*Y3+X3*Y1-X3*Y2-X1*Y3-X2*Y1=:=0,!.

concatenare([],L,L).
concatenare([H|T],L,[H|R]):-concatenare(T,L,R).

submultime([_,_],[]).
submultime([_,H2,H3|T],S):-concatenare([H2,H3],T,R1),submultime(R1,S).
submultime([H1,H2,H3|T],[H1,H2,H3|S]):-
    concatenare([H2,H3],T,R),
    submultime(R,S),
    coliniare(H1,H2,H3).

s([],[]).
s([_|T],S):-s(T,S).
s([H1|T],[H1|S]):-s(T,S).
