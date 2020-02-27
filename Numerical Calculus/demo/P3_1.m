%% Ecuatia de gradul 2
% Rezolva ecuatia $ax^2+bx +c=0$, cu valorile de mai jos ale lui $a$,
% $b$, $c$

a=1; c=1;
b=-(1e7+1e-7);

%% Rezolvare cu aplicarea formulei, afectata de anulare flotanta

[x1,x2]=quadform(a,b,c)

%% Rezolvare cu relatiile lui Viete 
% $x_{1}x_{2}=\frac{c}{a}$

[x1b,x2b]=quadform2(a,b,c)