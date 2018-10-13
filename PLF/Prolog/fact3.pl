%(i,i)(i,o)
fact3(N,F):-N>0,
    N1 is N-1,
    fact3(N1,F1),
    F is N*F1.
fact3(0,1).
