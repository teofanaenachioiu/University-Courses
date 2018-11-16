elimin([],_,[]):-!.
elimin([_],1,[]):-!.
elimin([E],0,[E]):-!.
elimin([H1,H2|T],_,R):-H1<0,H2<0,!,elimin([H2|T],1,R).
elimin([H1,H2|T],0,[H1|R]):-H1>=0,H2>=0,!,elimin([H2|T],0,R).
elimin([H1,H2|T],1,R):-H1<0,H2>=0,!,elimin([H2|T],0,R).
elimin([H1,H2|T],_,[H1|R]):-elimin([H2|T],0,R).
