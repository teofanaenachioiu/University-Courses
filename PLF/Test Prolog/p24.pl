sterge([], _, []):-!.

sterge([E], F, [E]):-
     F =:= 0,!.

sterge([_], F, []):-
     F =\= 0,!.

sterge([H1,H2|T], _, LR):-
     H1 =:= H2-1,!,
     F1 is 1,
     sterge([H2|T], F1, LR).

sterge([H1,H2|T], F, [H1|LR]):-
     H1 =\= H2-1,
     F =:= 0,!,
     sterge([H2|T], F, LR).


sterge([H1,H2|T], F, LR):-
     H1 =\= H2-1,
     F =:= 1,
     F1 is 0,
     sterge([H2|T], F1, LR).

stergere(L,R):-sterge(L,0,R).
