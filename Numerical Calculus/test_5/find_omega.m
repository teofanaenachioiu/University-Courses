function omega=find_omega(A)
% determina valoarea optima a parametrului relaxarii
% A - matricea sistemului

D = diag(diag(A));
L = -tril(A,-1);
U = -triu(A,1);
T = D\(L+U);
val_prop = eig(T);
raza = max(abs(val_prop)); %raza spectrala a matricei Jacobi
omega = 2/(1+sqrt(1-raza^2));