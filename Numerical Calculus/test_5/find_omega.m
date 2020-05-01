function omega=find_omega(A)
% determina valoarea optima a parametrului relaxarii
% A - matricea sistemului

D = diag(diag(A));
M=D;
N=M-A;
T=M\N;
val_prop = eig(T);
raza = max(abs(val_prop)); %raza spectrala a matricei Jacobi
omega = 2/(1+sqrt(1-raza^2));