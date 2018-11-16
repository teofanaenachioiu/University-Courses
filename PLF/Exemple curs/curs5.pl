p([],0).
p([H|T],S):-H>0,!,p(T,S1),S is S1+H.
p([_|T],S):-p(T,S).

comb([H|_],1,[H]).
comb([_|T],K,C):-comb(T,K,C).
comb([H|T],K,[H|C]):-K>1,K1 is K-1, comb(T,K1,C).

inserare(E,L,[E|L]).
inserare(E,[H|T],[H|L]):-inserare(E,T,L).

permutari([],[]).
permutari([H|T],L):-permutari(T,L1),inserare(H,L1,L).