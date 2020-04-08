function x = SOR(A,b,omega,err)
% metoda relaxarii (Successive OverRelaxation)
% A - matricea sistemului
% b - vectorul termenilor liberi
% omega - parametrul relaxarii
% x0 - vector de pornire
% err - toleranta (implicit 1e-3)
% nitmax - numarul maxim de iteratii (implicit 50)
% z - solutia
% ni -numarul de iteratii realizat efectiv

if (omega<=0) || (omega>=2)
    error('parametrul relaxarii ilegal')
end
[m,n]=size(A);
x0=zeros(size(b));
if (m~=n) || (n~=length(b))
    error('dimensiuni ilegale')
end
%calculul lui T si c (pregatirea iteratiilor)
M = 1/omega*diag(diag(A))+tril(A,-1);
N = M-A;
T = M\N;
c = M\b;
alfa = norm(T,inf);
x = x0(:);
true = 1;
while true==1
    x0 = x;
    x = T*x0+c;
    if norm(x-x0,inf)<(1-alfa)/alfa*err
        true = 0;
    end
end