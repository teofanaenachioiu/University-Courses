%(i,i)(o,i)
member1(E,[E|_]).
member1(E,[_|L]):-member1(E,L).

go1:-member1(1,[1,2,1,3,1,4]).
