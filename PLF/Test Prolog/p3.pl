fct(A,A,A):-!.
fct(A,B,X):-A>B,!,A1 is A-B, fct(A1,B,X).
fct(A,B,X):-B>A,B1 is B-A, fct(A,B1,X).



cmmdc([],C,C):-!.
cmmdc([H|T],C,Rez):-abs(H,H1),fct(H1,C,X),cmmdc(T,X,Rez).

main([],1):-!.
main([H|T],R):-abs(H,H1),cmmdc(T,H1,R).
