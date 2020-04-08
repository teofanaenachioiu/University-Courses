function [A,b] = generare_sistem_2(n)
% genereaza al doilea sistem
% n - dimensiunea sistemului
% A - matricea sistemului
% b - matricea termenilor liberi

v = repmat(5,n,1);
u = repmat(-1,n-1,1);
u1 = repmat(-1,n-3,1);
A = diag(v) + diag(u,1) + diag(u,-1) + diag(u1,3) + diag(u1,-3);
b = repmat(1,n,1);
b(1) = 3; b(2) = 2; b(3) = 2;
b(n) = 3; b(n-1) = 2; b(n-2) = 2;