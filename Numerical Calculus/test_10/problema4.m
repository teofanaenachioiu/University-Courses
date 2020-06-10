i0 = 100;
R = 0.5;
t0 = 0.001;
n = 16;


i = @(t) i0.*exp(-t./t0).*sin(2.*t./t0);
f = @(t) R.*(i(t)).^2;
Im = integral(f,0,Inf)

% am aplicat schimbarea de variabila 2t/t0 = x
% alpha = 0
E = @(x) sin(x).^2;
a=0;
[g_nodes,g_coeff]=Gauss_Laguerre(n,a);
I = 50*R*i0^2*vquad(g_nodes,g_coeff,E)

syms t;
rest = vpa(1/factorial(2*n)*int(exp(-t)*laguerreL(n,t)^2,t,sym(0),sym(inf))*R*i0^2)