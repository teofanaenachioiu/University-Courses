inversareAux([],LP,LP):-!.
inversareAux([H|T],LP,LF):-LP1 = [H|LP],inversareAux(T,LP1,LF).

inversare(L,LF):-inversareAux(L,[],LF).

succesorAux([],Trans,[Trans]):-Trans=:=1,!.
succesorAux([],Trans,[]):-Trans=:=0,!.
succesorAux([H|T],Trans,[H1|LP]):-
    H1 is mod(H+Trans,10),
    Trans1 is div(H+Trans,10),
    succesorAux(T,Trans1,LP).

succesor(L,Rez):-inversare(L,Li),succesorAux(Li,1,Lp),inversare(Lp,Rez).

lista([],[]):-!.
lista([H|T],[Suc|LP]):-is_list(H),!,succesor(H,Suc),lista(T,LP).
lista([H|T],[H|LP]):-not(is_list(H)),lista(T,LP).
