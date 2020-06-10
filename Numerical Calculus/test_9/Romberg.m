function [I,kk] = Romberg(f,a,b, precision, nmax)
% determinarea aproximativa a integralei prin metoda Romberg
% f -functia
% a,b - limitele de integrare
% precision - precizia calculului
% nmax - numar iteratii

if nargin < 5
    nmax = 10; % valoare default
end

if nargin < 4
    precision = 1e-3; % valoare default
end

h = b-a;

R(1,1) = h/2*(f(a)+f(b));
kk = 2;
I = R;

for k=2:nmax    
    x = a+([1:2^(k-2)]-1/2)*h;
    R(k,1) = 1/2*(R(k-1,1) + h*sum(f(x)));
    kk = kk +1;
    pwr = 4;
    for j = 2:k
        R(k,j) = (pwr * R(k,j-1) - R(k-1,j-1))/(pwr-1);
        pwr = pwr * 4;
    end
    if (abs(R(k,k) - R(k-1,k-1))<precision)
        I = R(k,k);
        return
    end
    
    %dublam nodurile
    h = h/2;
end