function x = Jacobi(A,b,err)
% metoda lui Jacobi
% A - matricea sistemului
% b - vectorul termenilor liberi
% err - toleranta (implicit 1e-3)
% nitmax - numarul maxim de iteratii (implicit 50)
% x - solutia
% ni -numarul de iteratii realizat efectiv

[m,n]=size(A);
x0 = zeros(size(b));
if (m~=n) || (n~=length(b))
   error('dimensiuni ilegale')
end
%calculul lui T si c (pregatirea iteratiilor)
M = diag(diag(A));
N = M - A;
T = inv(M)*N;
c = inv(M)*b;
alfa = norm(T,inf);
x=x0(:);

true = 1;
while true==1
   x0 = x;
   x = T*x0+c;
   if norm(x-x0,inf)<(1-alfa)/alfa*err
      true = 0;
   end
end