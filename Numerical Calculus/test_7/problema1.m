% Sa se reprezinte grafic functia lambda=..., unde k(x), k = 0,...,m 
% sunt polinoamele fundamentale Lagrange corespunzatoare
% nodurilor xk, k = 0,...,m.

close all
syms t
a=0;
b=10;
m = 10;
x=linspace(a,b,m);
P=Lagrange(x,t);
fplot(P,[a,b])
axis([a b 0 10])
hold off