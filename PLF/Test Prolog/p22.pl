invers([],Col,Col):-!.
invers([H|T],Col,R):-Coll=[H|Col],invers(T,Coll,R).

prod([],_,0,[]):-!.
prod([],_,Trans,[Trans]):-!.
prod([H|T],E,Trans,[Nr|R]):-
    Nr is mod(H*E+Trans,10),
    Trans1 is div(H*E+Trans,10),
    prod(T,E,Trans1,R).

produs(L,E,Rez):-invers(L,[],Inv),prod(Inv,E,0,R),invers(R,[],Rez).
