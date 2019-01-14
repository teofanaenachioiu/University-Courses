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


candidat(E,[E|_]).
candidat(E,[_|T]):-candidat(E,T).

apare(E,[E|_]):-!.
apare(E,[_|T]):-apare(E,T).

subm(L,S,R):-candidat(E,L),E=<S,submAux(L,S,[E],E,R).

submAux(_,S,Col,S,Col):-!.
submAux(L,S,[H|Col],Sp,R):-
    candidat(E,L),
    H<E,
    not(apare(E,Col)),
    S1 is Sp+E,
    S1 =<S,
    submAux(L,S,[E,H|Col],S1,R).

