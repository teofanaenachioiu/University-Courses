apare(E,[E|_]):-!.
apare(E,[_|T]):-apare(E,T).

adaugaSf([],E,[E]):-!.
adaugaSf([H|T],E,[H|Rez]):-adaugaSf(T,E,Rez).

multime([],Col, Col):-!.
multime([H|T],Col,Rez):-
    not(apare(H,Col)),!,
    adaugaSf(Col,H,Coll),
    multime(T,Coll,Rez).
multime([H|T],Col,Rez):-
    apare(H,Col),
    multime(T,Col,Rez).
