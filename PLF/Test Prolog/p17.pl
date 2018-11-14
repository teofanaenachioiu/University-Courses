swap([X,Y|T],[Y,X|T]):-X>Y,!.
swap([X,X|T],[X|T]):-!.
swap([H|T],[H|R]):-swap(T,R).

mySort(L,S):-swap(L,L1),!,mySort(L1,S).
mySort(L,L).
