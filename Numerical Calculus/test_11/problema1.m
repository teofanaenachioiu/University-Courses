syms x;
f = @(x) 2.*x.*besselj(1,x)-besselj(0,x);
diff(2.*x.*besselj(1,x)-besselj(0,x)) % derivata functiei
fd = @(x) 3.*besselj(1, x) - 2.*x.*(besselj(1, x)./x - besselj(0, x));

ea = 0;
er = 1e-9;

x0 = [0.9;3.9;7]; % aproximatia initiala facuta pe baza graficului
[z,ni] = Newton(f,fd,x0,ea,er)

x0 = [0,4,7]; % valori de start
x1 = [1,5,8];
[z1,ni1] = secant(f,x0,x1,ea,er)

% afisare grafic si solutii
fplot(f,[0,10]); grid on; hold on;
plot(z,[0,0,0],'r*'); hold on;
plot(z1,[0,0,0],'g*');
legend('functia', 'solutii newton', 'solutii secanta')