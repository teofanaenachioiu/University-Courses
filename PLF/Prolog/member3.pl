%(i,i),(o,i)
member3(E,[_|L]):-member3(E,L).
member3(E,[E|_]).

