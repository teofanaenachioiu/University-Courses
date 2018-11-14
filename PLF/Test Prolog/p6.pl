%adaugare(L,E,Poz,P)
adaugare([],_,_,_,[]):-!.
adaugare([H|T],V,Poz,Nr,[H|R]):-Poz<Nr,!,Poz1 is Poz+1,adaugare(T,V,Poz1,Nr,R).
adaugare([H|T],V,Poz,Nr,[H,V|R]):-Poz=Nr,Poz1 is Poz+1,Nr1 is Nr*2,adaugare(T,V,Poz1,Nr1,R).

