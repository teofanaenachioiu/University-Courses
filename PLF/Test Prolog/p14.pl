%invers(L,R)
invers([],Col,Col):-!.
invers([H|T],Col, R):-Coll=[H|Col],invers(T,Coll,R).


adunareAux([],[],1,[1]):-!.
adunareAux([],[],0,[]):-!.
adunareAux([H|T],[],Trans,[Nr|R]):-
    Nr is mod(H+Trans,10),
    Trans1 is div(H+Trans,10),!,
    adunareAux(T,[],Trans1,R).
adunareAux([],[H|T],Trans,[Nr|R]):-
    Nr is mod(H+Trans,10),
    Trans1 is div(H+Trans,10),!,
    adunareAux([],T,Trans1,R).
adunareAux([H1|T1],[H2|T2],Trans,[Nr|R]):-
    Nr is mod(H1+H2+Trans,10),
    Trans1 is div(H1+H2+Trans,10),!,
    adunareAux(T1,T2,Trans1,R).

%adunare(L1,L2,R)

adunare(L1,L2,R):-
    invers(L1,[],R1),
    invers(L2,[],R2),
    adunareAux(R1,R2,0,Rez),
    invers(Rez,[],R).
