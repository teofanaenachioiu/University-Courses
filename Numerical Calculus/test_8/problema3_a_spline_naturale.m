% 3. Sa se aproximeze functia f(x)=arctan x, pe [-1,1];
% (a) Folosind toate cele patru tipuri de spline, noduri 
% echidistante si noduri Cebisev de speta I, n = 15.

f = @(x) atan(x);
fd = @(x) 1./(x.^2 + 1);
fdd = @(x) -(2.*x)./(x.^2 + 1).^2;

a = -1;
b = 1;

t = linspace(a,b,2000);
m = 15;

x = get_nodes_echi(m,a,b);
y = f(x);
coef = SplineNaturale(x,y);
px = get_poly_spline(x,coef,t);
ft = f(t');

subplot(2,2,1)
plot(t,f(t),t,px)
legend('functia','spline deriv secunde');
title('Noduri echidistante')

subplot(2,2,2)
plot(t',abs(ft-px))
legend('eroarea');
title('Eroarea')

x = get_nodes_cebisev_1(m,a,b);
y = f(x);
coef = SplineNaturale(x,y);
px = get_poly_spline(x,coef,t);
ft = f(t');

subplot(2,2,3)
plot(t,f(t),t,px)
legend('functia','spline deriv secunde');
title('Noduri cebisev I')

subplot(2,2,4)
plot(t',abs(ft-px))
legend('eroarea');
title('Eroarea')