apare([H|_],H):-!.
apare([_|T],E):-apare(T,E).

multimeAux([],_,[]):-!.
multimeAux([H|T],Col,R):-apare(Col,H),!,Coll=[H|Col],multimeAux(T,Coll,R).
multimeAux([H|T],Col,[H|R]):-not(apare(Col,H)),!,Coll=[H|Col],multimeAux(T,Coll,R).

multime(L,R):-multimeAux(L,[],R).

desc([],[],[],Nrp,Nrp,Nri,Nri):-!.
desc([H|T],[H|Li],Lp,Nrp,Nrpf,Nri,Nrif):-
    Rest is mod(H,2),
    Rest=1,!,
    Nri1 is Nri+1,
    desc(T,Li,Lp,Nrp,Nrpf,Nri1,Nrif).
desc([H|T],Li,[H|Lp],Nrp,Nrpf,Nri,Nrif):-
    Nrp1 is Nrp+1,
    desc(T,Li,Lp,Nrp1,Nrpf,Nri,Nrif).

descompunere(L,R,NrPare,NrImpare):-desc(L,Li,Lp,0,NrPare,0,NrImpare),R=[Lp,Li].
