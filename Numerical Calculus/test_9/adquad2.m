function [I,k] = adquad2(f,a,b,tol)
% determina valoarea aproximativa a integralei
% f - functia
% a,b - limitele
% tol - toleranta
% I - valoarea aprox a integralei
% k - numarul de evaluari de functii

if nargin < 4
    tol = 1e-3; 
end

c = (a + b)/2;
fa = f(a);
fb = f(b); 
fc = f(c); 

[I,k] = quadstep(f, a, b, tol, fa, fc, fb);
k = k + 3;
