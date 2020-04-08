function [A,b] = generare_sistem_SPD(n)
% genereaza un sistem ce are matrice simetric pozitiv definita(SPD)
% n - dimensiune matrice
% A - matricea sistemului
% b - matrice coloana rezultat


% Construim matricea A = Q'DQ, unde Q este o matrice random si D este
% o matrice diagonala cu elemente pozitive aleatoare. 

Q = randn(n,n);

eigen_mean = 2; % utilizat pentru shiftare

A = Q' * diag(abs(eigen_mean+randn(n,1))) * Q;

% generez matricea coloana rezultat
b = (1:n);
b = A * (b');



