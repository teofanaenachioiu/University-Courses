mysort(List,SortedList):-
    swap(List,List1),!,
    mysort( List1, SortedList) .
mysort(List,List).

swap([X,Y|T],[Y,X|T]):-X>Y,!.
swap([H|T],[H|R]):-swap(T,R).
