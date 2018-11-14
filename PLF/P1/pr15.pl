nrElem([],Ok,Ok):-!.
nrElem([_|T],0,F):-nrElem(T,1,F),!.
nrElem([_|T],1,F):-nrElem(T,0,F).


nrParElem(L):-nrElem(L,0,Ok), Ok = 0.

minimA([],M,M).
minimA([H|T],M,Mf):-H<M,!,minimA(T,H,Mf).
minimA([_|T],M,Mf):-minimA(T,M,Mf).

minim([H|T],M):-minimA(T,H,M).

eliminA([],_,_,[]):-!.
eliminA([E|T],E,1,R):-eliminA(T,E,0,R),!.
eliminA([E|T],E,0,[E|R]):-eliminA(T,E,0,R),!.
eliminA([H|T],E,Ok,[H|R]):-eliminA(T,E,Ok,R).

elimin(L,R):-minim(L,M),eliminA(L,M,1,R).
