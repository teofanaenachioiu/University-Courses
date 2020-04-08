function x = cramer(A, b)
% CRAMER - metoda lui cramer de determinare a solutiilor unui sistem
% A - matricea sistemului 
% b - vectorul termenilor liberi
% x - rezultatul sistemului

d = determinant(A);
x = zeros(size(b));
[n,n] = size(A);

for i=1:n
    Ax = A;
    Ax(:,i)= b(:,1);
    dx = determinant(Ax);
    x(i) = dx/d;
end