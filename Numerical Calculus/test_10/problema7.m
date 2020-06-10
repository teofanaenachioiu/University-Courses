a = 0;
b = 1;

% cuaduratura adaptiva
% paplicam schimbarea de variabila x=t^2
% capetele intervalului sunt 0,1
f1 = @(x) 2.*cos(x.^2);
f2 = @(x) 2.*sin(x.^2);
[IA1,cntA1] = adquad2(f1,a,b)
[IA2,cntA2] = adquad2(f2,a,b)

% cuadratura Gauss-Legendre
% aplicam schimbarea de variabila x = t^2
% dupa prelucrarea rezultatului intervalul devine -1,1
n = 10;
f12 = @(t) cos(t.^2);
f22 = @(t) sin(t.^2);
[g_nodes,g_coeff] = Gauss_Legendre(n);
I1 = vquad(g_nodes,g_coeff,f12)
I2 = vquad(g_nodes,g_coeff,f22)

% cuadratura Gauss-Jacobi 
% aplicam schimbarea de variabila x = (t+1)/2
% alpha = 0, beta = -1/2
n = 10;
f13 = @(x) sqrt(2)/2.*cos((x+1)./2);
f23 = @(x) sqrt(2)/2.*sin((x+1)./2);
a = 0;
b = -1/2;
[g_nodes,g_coeff] = Gauss_Jacobi(n,a,b);
I1 = vquad(g_nodes,g_coeff,f13)
I2 = vquad(g_nodes,g_coeff,f23)