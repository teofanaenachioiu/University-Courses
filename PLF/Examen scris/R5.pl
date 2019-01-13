prim_aux(N,D,0):-D>N,!.
prim_aux(N,D,Nrd):-R is N mod D, R =0,!, D1 is D+1, prim_aux(N,D1,Nrd1),Nrd is Nrd1+1.
prim_aux(N,D,Nrd):-D1 is D+1,prim_aux(N,D1,Nrd).

prim(N):- prim_aux(N,1,2).

elimin([],_,[]):-!.
elimin([H|T],N,[H|R]):-not(prim(H)),!,elimin(T,N,R).
elimin([H|T],0,[H|R]):-!,elimin(T,0,R).
elimin([_|T],N,R):-N1 is N-1,elimin(T,N1,R).

submultimi([],[]).
submultimi([_|T],S):-submultimi(T,S).
submultimi([H|T],[H|S]):-submultimi(T,S).

suma([],0):-!.
suma([H|T],S):-S1 is S-H,S1>=0,suma(T,S1).

submulSuma(L,S,R):-submultimi(L,R),suma(R,S).

toateSub(L,S,R):-findall(Sub,submulSuma(L,S,Sub),R).
