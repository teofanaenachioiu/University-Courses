function x = SOR(A,b,omega,precizia)
% metoda relaxarii (Successive OverRelaxation)
% A - matricea sistemului
% b - vectorul termenilor liberi
% omega - parametrul relaxarii
% precizia - precizia calculului
% x - solutia

[m,n]=size(A);
x0=zeros(n,1);

% A = D+L+U
D = diag(diag(A));
L = -tril(A,-1);
U = -triu(A,1);

%calculul lui T si c (pregatirea iteratiilor)
T = (inv(D - omega*L))*((1-omega)*D + omega*U);
c = omega*(inv(D - omega*L))*b;

toleranta = (1-norm(T,inf))/norm(T,inf)*precizia;
x = x0(:);

while 1==1
    x0 = x;
    x = T*x0+c ;
    if norm(x-x0,inf) <= toleranta
        break;
    end
end