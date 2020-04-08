function [A,b] = generare_matrici(n)
% genereaza sisteme de dimensiune n
% A - matrice de dimensiune n x n
% b - matrice coloana de dimensiune n

max_val = 50;

% generez random elementele matricei A cand timp 
% matricea e singulara (adica det(A)=0).
while true
  A = ceil(unifrnd(-max_val, max_val,n));
  if det(A) ~= 0; break; end 
end

% pentru ca solutia sa fie [1,...,1]T trebuie ca
% suma b(i) sa fie egal cu suma elementelor de pe
% linia i din matricea A
b = sum(A,2);
