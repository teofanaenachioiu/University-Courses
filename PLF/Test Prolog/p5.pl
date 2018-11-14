apare(E,[E|_]):-!.
apare(E,[_|T]):-apare(E,T).

multime([],[]):-!.
multime([H|T],[H|R]):-not(apare(H,T)),multime(T,R).
multime([H|T],R):-apare(H,T),multime(T,R).
