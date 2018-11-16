sterge([],_,[]):-!.
sterge([E],0,[E]):-!.
sterge([_],1,[]):-!.
sterge([H1,H2|T],0,R):-
    H1>H2,!,
    sterge([H2|T],1,R).
sterge([H1,H2|T],1,R):-
    H1>H2,!,
    sterge([H2|T],1,R).
sterge([H1,H2|T],1,R):-
    H1<H2,!,
    sterge([H2|T],0,R).
sterge([H1,H2|T],0,[H1|R]):-
    H1<H2,!,
    sterge([H2|T],0,R).