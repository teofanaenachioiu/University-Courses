% Sa se rezolve sistemul n x n cu cu metodele Jacobi, Gauss-Seidel 
% si SOR, pentru n = 100, cu o precizie de 10^-6.

n = 1000;
err = 10^-6; % precizia
A=spdiags([-ones(n,1),2*ones(n,1),-ones(n,1)],-1:1,n,n); % matricea sist
b=A*ones(n,1); % vectorul termenilor liberi
x0 = zeros(size(b)); % solutia initiala
xe = ones(n,1); % solutia exacta
nriter = 500000; % numar maxim de iteratii

disp('Metoda Jacobi')
[x_jacobi,nri] = Jacobi(A,b,x0,nriter,err);
norm(b-A*x_jacobi)
norm(b-A*x_jacobi)/norm(b)
errel=norm(xe-x_jacobi)/norm(xe)
errel <  err
% repetam iteratia cu solutia gasita anterior ca vector initial 
% pentru a imbunatati precizia
[x_jacobi,nri] = Jacobi(A,b,x_jacobi,nriter,err);
errel=norm(xe-x_jacobi)/norm(xe)
errel <  err

disp('Metoda Gauss-Seidel')
[x_gauss_seidel, nri] = Gauss_Seidel(A,b,x0,nriter,err);
norm(b-A*x_gauss_seidel)
norm(b-A*x_gauss_seidel)/norm(b)
errel = norm(xe-x_gauss_seidel)/norm(xe)
errel <  err
% repetam iteratia cu solutia gasita anterior ca vector initial 
% pentru a imbunatati precizia
[x_gauss_seidel,nri] = Gauss_Seidel(A,b,x_gauss_seidel,nriter,err);
errel = norm(xe-x_jacobi)/norm(xe)
errel <  err

disp('Metoda SOR')
omega = find_omega(A);
[x_sor, nri]  = SOR(A,b,omega,x0,nriter,err);
norm(b-A*x_sor)
norm(b-A*x_sor)/norm(b)
errel=norm(xe-x_sor)/norm(xe)
errel <  err
% repetam iteratia cu solutia gasita anterior ca vector initial 
% pentru a imbunatati precizia
[x_sor, nri]  = SOR(A,b,omega,x_sor,nriter,err);
errel=norm(xe-x_sor)/norm(xe)
errel <  err
