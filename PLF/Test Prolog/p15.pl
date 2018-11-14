invers([],Col,Col):-!.
invers([H|T],Col,R):-Coll=[H|Col],invers(T,Coll,R).

dif([],[],1,[9]):-!.
dif([],[],0,[]):-!.
dif([H1|T1],[H2|T2],I,R):-
    H1-I<H2,!,
    Nr is H1-I-H2+10,
    dif(T1,T2,1,R1),
    R = [Nr|R1].
dif([H1|T1],[H2|T2],I,R):-
    H1-I>=H2,!,
    Nr is H1-I-H2,
    dif(T1,T2,0,R1),
    R = [Nr|R1].
dif([H|T],[],I,R):-
    H-I<0,!,
    Nr is H-I+10,
    dif(T,[],1,R1),
    R = [Nr|R1].
dif([H|T],[],I,R):-
    H-I>=0,!,
    Nr is H-I,
    dif(T,[],0,R1),
    R = [Nr|R1].

validare([0|T],T):-!.
validare([_|T],[_|T]):-!.
validare([],[]).

%egale(L1,L2,R)
egale([],[],1):-!.
egale([H1|_],[H2|_],1):-H1>H2,!.
egale([H1|_],[H2|_],2):-H1<H2,!.
egale([_|T1],[_|T2],OK):-egale(T1,T2,OK).

%mare(L1,L2,R)
mare([],[],0):-!.
mare([],[_|_],2):-!.
mare([_|_],[],1):-!.
mare([_|T1],[_|T2],OK):-mare(T1,T2,OK).

maiMare(L1,L2,OK):-mare(L1,L2,1),OK is 1,!.
maiMare(L1,L2,OK):-mare(L1,L2,2),OK is 2,!.
maiMare(L1,L2,OK):-egale(L1,L2,OK).

diferenta(L1,L2,R):-
    invers(L1,[],L11),
    invers(L2,[],L22),
    maiMare(L1,L2,OK),
    OK is 1,!,
    dif(L11,L22,0,R1),
    invers(R1,[],Rpr),
    validare(Rpr,R).

diferenta(L1,L2,R):-
    invers(L1,[],L11),
    invers(L2,[],L22),
    dif(L22,L11,0,R1),
    invers(R1,[],Rpr),
    validare(Rpr,R11),
    R=[-|R11].






