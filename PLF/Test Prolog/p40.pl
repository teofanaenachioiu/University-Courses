sterg([],_,_,[]):-!.
sterg([_|T],Nr,C,R):-Nr =:=C-1,!,Nr1 is Nr+1, C1 is C*2,sterg(T,Nr1,C1,R).
sterg([H|T],Nr,C,[H|R]):-Nr1 is Nr+1,sterg(T,Nr1,C,R).

stergere(L,R):-sterg(L,1,2,R).
