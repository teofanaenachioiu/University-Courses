function A = get_sym_matrix(n)
% creaza o matrice simetrica de dimensiune n
% n - dimensiunea dorita
% A - matrice simetrica generata random

%max_val = 100; % valoarea maxima pe care o poate lua un element
%A = ceil(unifrnd(1, max_val,n)); % generam o matrice random
%A = tril(A); % decupam triunghiul de jos
%A = A + triu(A.',1); % adunam la matrice transpusa triunghiului de jos
d = 1000000*rand(n,1); % The diagonal values
t = triu(bsxfun(@min,d,d.').*rand(n),1); % The upper trianglar random values
A = diag(d)+t+t.'; % Put them together in a symmetric matrix
