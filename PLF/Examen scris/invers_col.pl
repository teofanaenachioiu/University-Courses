invers_aux([],Col,Col).
invers_aux([H|T],Col,R):-invers_aux(T,Col1,R),Col1=[H|Col].

invers(L,R):-invers_aux(L,[],R).
