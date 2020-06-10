n = 10;
f = @(x) x.*exp(-x.^2);

[g_nodes,g_coeff] = Gauss_Cheb1(n);
I = vquad(g_nodes,g_coeff,f);

ff = @(x) f(x)./sqrt(1-x.^2);
Im = integral(ff,-1,1);

diferanta = abs(Im-I)

syms t;
rest = 1/factorial(2*n)*int(chebyshevT(n,t)^2./sqrt(1-t^2),t,-1,1);
rest = double(vpa(rest))

