% Se dau N puncte in plan (prin coordonatele lor).
% Se cere sa se determinetoate submultimile de puncte coliniare.

coord([H|T],H,Y):-coord(T,H,Y),!.
coord([H|_],_,H):-!.

suntColiniare(J,K,L):-coord(J,X1,Y1),coord(K,X2,Y2),coord(L,X3,Y3),X1*Y2+X2*Y3+X3*Y1-X3*Y2-X1*Y3-X2*Y1=:=0,!.
