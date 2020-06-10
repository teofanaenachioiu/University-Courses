function y_aprox = mcmmp(x, y, phiF, t)
% x - valorile nodurilor
% y - valoarea functiei in noduri
% phi - baza
% t - valorile de evaluat

A = phiF(x);
b = y';
c = A\b;
size(c)
y_aprox = transpose(c)*phiF(t);
   
