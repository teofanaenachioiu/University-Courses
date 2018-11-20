invers([],Col,Col):-!.
invers([H|T],Col,R):-Coll=[H|Col],invers(T,Coll,R).

adun([],_,0,[]):-!.
adun([],_,Trans,[Trans]):-!.
adun([H|T],N,Trans,[Nr|R]):-
    Nr is mod(H+N+Trans,10),
    Trans1 is div(H+N+Trans,10),
    adun(T,0,Trans1,R).

adunare(L,N,R):-invers(L,[],Li),adun(Li,N,0,Rp),invers(Rp,[],R).
