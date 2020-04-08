% Rezolvati sistemele cu toate metodele implementate.

n = 10;
err = 10^-6;

[A,b] = generare_sistem_1(n);

x_jacobi = Jacobi(A,b,err)
x_gauss_seidel = Gauss_Seidel(A,b,err)
omega = find_omega(A);
x_sor = SOR(A,b,omega,err)

[A1,b1] = generare_sistem_2(n);

x_jacobi1 = Jacobi(A1,b1,err)
x_gauss_seidel1 = Gauss_Seidel(A1,b1,err)
omega1 = find_omega(A1);
x_sor1 = SOR(A1,b1,omega1,err)

