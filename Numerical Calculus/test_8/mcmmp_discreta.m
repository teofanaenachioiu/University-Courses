function y_aprox = mcmmp_discreta(x, y, phiF, t)
% x - valorile nodurilor
% y - valoarea functiei in noduri
% phi - baza
% t - valorile de evaluat

phi_x = phiF(x);
phi_t = phiF(t);

A = phi_x*transpose(phi_x);
b = phi_x*transpose(y);

a = A\b;

y_aprox = transpose(a)*phi_t;
   
