numarare(_,[],NR,NR):-!.
numarare(E,[H|T],NR,NRF):-E=H,!,NR1 is NR+1,numarare(E,T,NR1,NRF).
numarare(E,[_|T],NR,NRF):-numarare(E,T,NR,NRF).

sterge(_,[],[]):-!.
sterge(E,[H|T],R):-H=E,!,sterge(E,T,R).
sterge(E,[H|T],[H|R]):-sterge(E,T,R).

aparitii([],[]):-!.
aparitii([H|T],R):-numarare(H,T,1,NR), sterge(H,T,St),aparitii(St,R1),R=[[H,NR]|R1].
