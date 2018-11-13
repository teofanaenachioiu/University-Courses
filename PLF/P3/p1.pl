% Se dau N puncte in plan (prin coordonatele lor).
% Se cere sa se determinetoate submultimile de puncte coliniare.

% determina abscisa si ordonata unui punct
% coord(L:list,X:integer,Y:integer)
% L - coordonatele punctului
% X - abscisa
% Y - ordonata
% model de flux (i,o,o)
coord([H1,H2],H1,H2):-!.

% determina daca 3 puncte sunt coliniare
% coliniare(J:list,K:list,L:list)
% J,K,L - punctele reprezentate ca liste
% model de flux (i,i,i)
coliniare(J,K,L):-
    coord(J,X1,Y1),
    coord(K,X2,Y2),
    coord(L,X3,Y3),
    X1*Y2+X2*Y3+X3*Y1-X3*Y2-X1*Y3-X2*Y1=:=0.

% determina toate sublistele unei liste
% sublista(L:list,S:list)
% L - lista initiala
% S - sublista
% model de flux (i,o)
sublista([],[]).
sublista([_|T],S):-sublista(T,S).
sublista([H1|T],[H1|S]):-sublista(T,S).

% determina daca punctele din lista sunt coliniare
% suntColiniare(L:list)
% L - lista de puncte de verificat
% model de flux (i)
suntColiniare([]):-!.
suntColiniare([_]):-!.
suntColiniare([_,_]):-!.
suntColiniare([H1,H2,H3|T]):-coliniare(H1,H2,H3),suntColiniare([H2,H3|T]).

% determina daca punctele din lista sunt coliniare,
% si lista contine cel putin 3 numere
% submultimeColiniare(L:list)
% L - lista de puncte de verificat
% model de flux (i)
submultimeColiniare([H1, H2, H3|T]):-
    suntColiniare([H1, H2, H3|T]).

% determina sublistele cu puncte coliniare
% submultimePuncteColiniare(L:list,R:list)
% L - lista initiala
% R - lista cu puncte coliniare
% model de flux (i,o)
submultimePuncteColiniare(L,R):-
    sublista(L,R),
    submultimeColiniare(R).

% determina toate sublistele cu puncte coliniare
% toatePunctele(L:list, R:list)
% L - lista initiala
% R - lista cu toate sublistele cu puncte coliniare
% model de flux (i,o)
toatePunctele(L,R):-findall(P,submultimePuncteColiniare(L,P),R).







