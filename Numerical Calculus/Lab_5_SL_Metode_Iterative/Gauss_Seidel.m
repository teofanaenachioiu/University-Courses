function z = Gauss_Seidel(A,b,err)
% metoda Gauss_Seidel
% A - matricea sistemului
% b - vectorul termenilor liberi
% err - toleranta (implicit 1e-3)
% nitmax - numarul maxim de iteratii (implicit 50)
% z - solutia
% ni -numarul de iteratii realizat efectiv

[m,n]=size(A);
if (m~=n) || (n~=length(b))
   error('dimensiuni ilegale')
end
%calculul lui T si c (pregatirea iteratiilor)
x = zeros(n,1);
M = tril(A);
N = M-A;
T = M\N;
c = M\b;
alfa = norm(T,inf);
true = 1;
i = 1;
while true ==1
   x(:,i+1) = T*x(:,i)+c;
   if norm(x(:,i+1)-x(:,i),inf)<(1-alfa)/alfa*err
      z=x(:,i+1);
      true = 0;
   end
   i = i+1;
end
