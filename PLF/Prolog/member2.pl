%(i,i),(o,i)
member2(E,[E|_]):-!.
member2(E,[_|L]):-member2(E,L).

go2:-member2(1,[1,2,1,3,1,4]).
