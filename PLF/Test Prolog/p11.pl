valeAux([],1):-!.
valeAux([_],1):-!.
valeAux([H1,H2],1):-H1<H2,!.
valeAux([H1,H2|T],OK):-H1>H2,OK=2,!,valeAux([H2|T],0).
valeAux([H1,H2|T],OK):-H1>H2,OK=0,!,valeAux([H2|T],OK).
valeAux([H1,H2|T],OK):-H1<H2,OK=0,!,OK1 is 1, valeAux([H2|T],OK1).
valeAux([H1,H2|T],OK):-H1<H2,OK=1,!,valeAux([H2|T],OK).

vale(L):-valeAux(L,2).
