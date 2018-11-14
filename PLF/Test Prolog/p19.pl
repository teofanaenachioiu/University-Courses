noDup([],[]):-!.
noDup([E],[E]):-!.
noDup([H1,H2|T],[H1|R]):-
    H1==H2,!,
    noDup(T,R).
noDup([H1,H2|T],[H1|R]):-noDup([H2|T],R).

interclasaree([],L,L):-!.
interclasaree(L,[],L):-!.
interclasaree([H1|T1],[H2|T2],[H1|R]):-
    H1<H2,!,
    interclasaree(T1,[H2|T2],R).
interclasaree([H1|T1],[H2|T2],[H2|R]):-
    H1>H2,!,
    interclasaree([H1|T1],T2,R).
interclasaree([H|T1],[H|T2],[H|R]):-
    interclasaree(T1,T2,R).

interclasare(L1,L2,R):-interclasaree(L1,L2,Rr),noDup(Rr,R).
