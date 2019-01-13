combSuma([H|_],1,H,[H]).
combSuma([_|T],K,S,L):-combSuma(T,K,S,L).
combSuma([H|T],K,S,[H|L]):-K>1,K1 is K-1, S1 is S-H, S1>0,combSuma(T,K1,S1,L).

comb([H|_],K,[H]):-K=1.
comb([_|T],K,C):-comb(T,K,C).
comb([H|T],K,[H|C]):-K>1, K1 is K-1,comb(T,K1,C).

suma([],0):-!.
suma([H|T],S):-suma(T,S1), S is S1+H.

combSuma1(L,K,S,C):-comb(L,K,C),suma(C,S).

candidat(E,[E|_]).
candidat(E,[_|T]):-candidat(E,T).

combSuma2(L,K,S,C):-candidat(E,L),E=<S,combSuma_aux(L,K,S,C,[E],1,E).

% combSuma_aux(L:list,K:number,S:number,C:list,Col:list,Lg:number,Sp:number)
% L - lista initiala de numere
% K - numarul de combinari
% S - suma submultimii
% C - combinarea rezultat
% Col - lista colectoare de numere pentru rezultatul final C
% Lg - lumgimea listei colectoare
% Sp - suma partiala a combinarii
combSuma_aux(_,K,S,Col,Col,K,S):-!.
combSuma_aux(L,K,S,C,[H|Col],Lg,Sp):-
    Lg<K,
    candidat(E,L),
    E<H,
    S1 is Sp+E,
    S1=<S,
    Lg1 is Lg+1,
   combSuma_aux(L,K,S,C,[E|[H|Col]],Lg1,S1).
