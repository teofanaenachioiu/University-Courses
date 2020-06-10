format long;

f = @(x) log(x)./(x.^2-2.*x+2);

a = 1; 
b = pi;

n0 = 9;
tol = 1e-8;


I = Gauss_Legendre_ab(f,n0,a,b,tol)
integral(f,a,b,'ArrayValued',true)
