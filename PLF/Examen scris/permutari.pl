candidat(N,N).
candidat(N,E):-N>1,N1 is N-1,candidat(N1,E).

apare([E|_],E):-!.
apare([_|T],E):-apare(T,E).

permutari(N,V,P):-candidat(N,E),perm_aux(N,V,P,[E],1).

% perm_aux(N:number,V:number,P:list,Col:list,Lg:number)
% N - numar permutari
% V - diferenta dintre doua numere consecutive
% P - permutare
% Col - permutare partiala
% Lg - lungime permutare
perm_aux(N,_,P,P,N):-!.
perm_aux(N,V,P,[H|Col],Lg):-
    candidat(N,E),
    not(apare([H|Col],E)),
    abs(E-H)>=V,
    Lg<N,
    Lg1 is Lg+1,
    perm_aux(N,V,P,[E|[H|Col]],Lg1).
