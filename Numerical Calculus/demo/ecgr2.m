%% ANULARE - Ecuatia de gradul al doilea
% Fie ecuatia  $x^2-10^8x+1=0$.
% Aceasta ecuatie pune probleme. 
% Anularea poate apare daca $b^2>>4ac$.
%%
format long
a=1; c=1; b=-100000000; %b=-1e8
% Calculam radacinile obisnuit
x1=(-b+sqrt(b^2-4*a*c))/(2*a)
x2=(-b-sqrt(b^2-4*a*c))/(2*a)
%%
% Apare anulare la calculul lui $x_2$.
% *Remediu:* amplificam cu conjugata
x1=(-b+sqrt(b^2-4*a*c))/(2*a)
x2a=2*c/(-b+sqrt(b^2-4*a*c))
%% 
% Acelasi rezultat se obtine cu |roots|
x=roots([a,b,c]);
x(1),x(2)
%% 
% Altfel
d = sqrt(b^2 - 4*a*c);
x1 = (-b - sign(b)*d) / (2*a);
x2 = c/a/x1;

