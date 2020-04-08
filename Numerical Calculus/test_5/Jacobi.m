function x = Jacobi(A,b,err)
% metoda lui Jacobi
% A - matricea sistemului
% b - vectorul termenilor liberi
% precizia - precizia calculului
% x - solutia

[m,n]=size(A);
x0 = zeros(size(b));

D = diag(diag(A));
L = -tril(A,-1);
U = -triu(A,1);

% calculul lui T si c (pregatirea iteratiilor)
M = D;
N = L+U;
T = M\N;
c = M\b;
toleranta = (1-norm(T,inf))/norm(T,inf)*err;
x=x0(:);

while 1==1
   x0 = x;
   x = T*x0+c;
   if norm(x-x0,inf) <= toleranta
      break;
   end
end