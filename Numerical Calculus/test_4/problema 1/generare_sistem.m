function [A,b] = generare_sistem(n)
% genereaza sisteme compatibil determinate de dimensiune n
% sistemul returnat are solutie unica
% A - matrice de dimensiune n x n
% b - matrice coloana de dimensiune n

% stabilim valoarea maxima pe care o poate avea un coeficient
max_val = 50;

% generez random elementele matricei A cand timp 
% matricea e singulara (adica det(A)=0).
while true
  A = ceil(unifrnd(-max_val, max_val,n));
  if det(A) ~= 0; break; end 
end

b = ceil(unifrnd(-max_val, max_val,[1 n]));
b = b.';
