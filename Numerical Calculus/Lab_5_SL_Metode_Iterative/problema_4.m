n = 50;
err = 10^-6;

[A,b] = get_sistem_matrice_diagonal_dominanta(n);

x_jacobi = Jacobi(A,b,err)
x_gauss_seidel = Gauss_Seidel(A,b,err)
omega = find_omega(A);
x_sor = SOR(A,b,omega,err)