%inverseaza o lista
%inversareAux(L:lista, LP:lista, LF:lista)
%L - lista de inversat
%LP - lista inversata partiala
%LF - lista inversata finala
%model de flux (i,i,o),(i,i,i)
inversareAux([],LP,LP):-!.
inversareAux([H|T],LP,LF):-LP1 = [H|LP],inversareAux(T,LP1,LF).

%inverseaza o lista
%inversare(L:lista, LF:lista)
%L - lista de inversat
%LF - lista inversata
%model de flux (i,o),(i,i)
inversare(L,LF):-inversareAux(L,[],LF).

%se incrementeaza prima cifra a numarului
%succesorAux(L:lista, Trans: int, LP:lista)
%L - numarul reprezentat sub forma de lista
%Trans - transporul rezultat prin adunarea a doua cifre de pe acceasi
% pozitie
%LP - numarul obtinut prin incrementarea primei cifre a numarului
%model de flux (i,i,o),(i,i,i)
succesorAux([],Trans,[Trans]):-Trans=:=1,!.
succesorAux([],Trans,[]):-Trans=:=0,!.
succesorAux([H|T],Trans,[H1|LP]):-
    H1 is mod(H+Trans,10),
    Trans1 is div(H+Trans,10),
    succesorAux(T,Trans1,LP).

%determina succesorul unui numar reprezentat ca lista de cifre
%succesor(L:lista, Rez:lista)
%L - numarul reprezentat sub forma de lista
%Rez - succesorul numarului
%model de flux (i,o),(i,i)
succesor(L,Rez):-inversare(L,Li),succesorAux(Li,1,Lp),inversare(Lp,Rez).

%se incrementeaza toate numerele reprezentate ca liste de cifre
%lista(L:lista,LP:lista)
%L - lista care contine numere sau lista de cifre
% LP - lista rezultata prin incrementarea numerelor reprezentate sub
% forma de lista de cifre
%model de flux (i,o), (i,i)
lista([],[]):-!.
lista([H|T],[Suc|LP]):-is_list(H),!,succesor(H,Suc),lista(T,LP).
lista([H|T],[H|LP]):-not(is_list(H)),lista(T,LP).
