adaugare(L,E,R):-adaug(L,E,3,1,1,R).

adaug([],E,I,_,I,[E]):-!.
adaug([],_,_,_,_,[]):-!.
adaug([H|T],E,Poz,P,I,R):-
    Poz =I,!,
    Poz1 is Poz+P*2,
    P1 is P*2,
    I1 is I+1,
    adaug(T,E,Poz1,P1,I1,R1),
    R =[H,E|R1].
adaug([H|T],E,Poz,P,I,R):-
    I1 is I+1,
    adaug(T,E,Poz,P,I1,R1),
    R =[H|R1].

apare(E,[E|_]):-!.
apare(E,[_|T]):-apare(E,T).

candidat(N,N).
candidat(N,E):-N>1,N1 is N-1,candidat(N1,E).

permutari(N,V,R):-candidat(N,E),perm(N,V,[E],1,R).

perm(N,_,R,N,R):-!.
perm(N,V,[H|Col],Lg,R):-
    candidat(N,E),
    not(apare(E,[H|Col])),
    D is abs(E-V),
    D =< V,
    Lg<N,
    Lg1 is Lg+1,
    perm(N,V,[E,H|Col],Lg1,R).

toatePermAux(N,_,I,[]):-I=:=2*N-1,!.
toatePermAux(N,V,I,R):-
    findall(P,permutari(I,V,P),RR),
    Next is I+1,
    toatePermAux(N,V,Next,R1),
    R =[RR,R1] .

toatePerm(N,V,R):-toatePermAux(N,V,N,R).

