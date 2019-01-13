lungime([],0):-!.
lungime([_|T],N):-lungime(T,N1),N is N1+1.

elimina([],_,[]):-!.
elimina(L,0,L):-!.
elimina([H|T],N,R):-is_list(H),lungime(H,NR),NR mod 2 =:=0,!,
    N1 is N-1,elimina(T,N1,R).
elimina([H|T],N,[H|R]):-elimina(T,N,R).

submultimi([],[]).
submultimi([_|T],R):-submultimi(T,R).
submultimi([H|T],[H|R]):-submultimi(T,R).

suma([],0):-!.
suma([H|T],S):-S1 is S-H,S1>=0,suma(T,S1).

submulSuma(L,S,R):-submultimi(L,R),suma(R,S).

toateSub(L,S,R):-findall(Sub,submulSuma(L,S,Sub),R).

