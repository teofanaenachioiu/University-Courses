% Generati un sistem aleator care are solutia [1,2,...,n]T
% si matrice SPD. Rezolvati-l prin descompunere Cholesky 
% (n = 271).
format short

n = 271;
[A,b] = generare_sistem_SPD(n);

x = rezolvare_cholesky(A,b);
xe = [1:n]';

[L,U,P] = lup(A);
norm_lup = norm(L*U-P*A) % verificare descompunere LUP
norm_rez = norm(b-A*x) % verificare rezultat

conditionare = cond(A)*eps % conditionarea matricei A 
errl = norm(x-xe)/norm(xe) % eroarea relativa
