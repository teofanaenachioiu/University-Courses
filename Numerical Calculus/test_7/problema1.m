% Sa se reprezinte grafic functia lambda=..., unde k(x), k = 0,...,m 
% sunt polinoamele fundamentale Lagrange corespunzatoare
% nodurilor xk, k = 0,...,m.

close all
a=-10;
b=10;
m = 500;
x=linspace(a,b,m);
t=linspace(a,b,1000); 
P=Lebesgue(x,t);
plot(t, P, '.') 
hold off