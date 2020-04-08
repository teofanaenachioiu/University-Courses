% Sa se rezolve sistemul n x n cu cu metodele Jacobi, Gauss-Seidel 
% si SOR, pentru n = 100, cu o precizie de 10^-6.

n = 100;
err = 10^-6;

o1=ones(n-1,1);
A=spdiags([-ones(n,1),2*ones(n,1),-ones(n,1)],-1:1,n,n);
b=A*ones(n,1);
xe = ones(n,1);

%disp('Metoda Jacobi')
%x_jacobi = Jacobi(A,b,err);
%norm(x_jacobi-xe)/norm(xe) < err 
%norm(b-A*x_jacobi)

%disp('Metoda Gauss-Seidel')
%x_gauss_seidel = Gauss_Seidel(A,b,err)
%norm(x_gauss_seidel-xe)/norm(xe) < err % eroarea relativa
%norm(b-A*x_gauss_seidel)

disp('Metoda SOR')
omega = find_omega(A)
x_sor = SOR(A,b,omega,err);
%norm(x_sor-xe)/norm(xe) < err % eroarea relativa
%norm(b-A*x_sor)

