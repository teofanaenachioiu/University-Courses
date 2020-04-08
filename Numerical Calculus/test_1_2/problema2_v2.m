% Problema 2
% 
% Sa se aproximeze derivata lui f(x) = exp(x) in x = 0 cu formula
% f'(x) = (exp(h)-1)/h pentru valori ale lui h de forma h = 10.^(-15:0) 
% si h = 2:01^-k, k = 10:54; reprezentati grafic eroarea, explicati 
% fenomenul si propuneti un remediu.

fde=@(h) abs((exp(h)-1)./h-1);
2*sqrt(eps) % minimul pentru functia initiala

figure(1)
h1=10.^(-15:0);
fd1=fde(h1);
loglog(h1,fd1)

figure(2)
k = 10 : 54;
h2 = 2.01.^(-k);
fd2=fde(h2);
loglog(h2,fd2)

% Remediere:
% Ca si "remediu" se foloseste forma simetrica (f(x+h)-f(x-h))/(2*h)

remediu = @(h) abs((exp(h)-exp(-h))./(2*h)-1);
(6*eps)^(1/3) % minimul pentru functia simetrica

figure(3)
h11=10.^(-15:0);
fd11=remediu(h11);
loglog(h11,fd11)

figure(4)
k = 10 : 54;
h22 = 2.01.^(-k);
fd22=remediu(h22);
loglog(h22,fd22)