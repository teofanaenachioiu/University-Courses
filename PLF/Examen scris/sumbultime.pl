submul([],[]).
submul([_|T],S):-submul(T,S).
submul([H|T],[H|S]):-submul(T,S).

progresie([_|[]]):-!.
progresie([_,_|[]]):-!.
progresie([H1,H2,H3|T]):-S is H1+H3, H2=:=S / 2,!, progresie([H2,H3|T]).
progresie([H1,H2,H3|T]):-S is H2+H3, H1=:=S / 2,!, progresie([H2,H3|T]).
progresie([H1,H2,H3|T]):-S is H1+H2, H3=:=S / 2, progresie([H2,H3|T]).


prog([H1,H2,H3|T]):-progresie([H1,H2,H3|T]).

crescatoare([_|[]]):-!.
crescatoare([H1|[H2|T]]):-H1<H2,crescatoare([H2|T]).


submultime(L,S):-submul(L,S),prog(S).
