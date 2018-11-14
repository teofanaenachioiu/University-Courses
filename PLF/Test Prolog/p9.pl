cautare(E,[E|_]):-!.
cautare(E,[_|T]):-cautare(E,T).

%eliminAp(E,L,R)
eliminAp(_,[],[]):-!.
eliminAp(E,[H|T],R):-E=H,!,eliminAp(E,T,R).
eliminAp(E,[H|T],[H|R]):-E\=H,eliminAp(E,T,R).

%elimin(L,R)
elimin([],[]):-!.
elimin([H|T],[H|R]):-not(cautare(H,T)),!,elimin(T,R).
elimin([H|T],R):-cautare(H,T),eliminAp(H,T,Rrr),elimin(Rrr,R).
