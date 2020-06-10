% 3. Sa se aproximeze functia f(x)=arctan x, pe [-1,1];
% (b) Folosind metoda celor mai mici patrate discreta si 
% noduri echidistante,n = 10.

a = -1;
b = 1;
f = @(x) atan(x);
m = 15;

x = get_nodes_echi(m, a, b); % x = linspace(a, b, m);
y = f(x);

t = linspace(a, b, 1000);
yy = f(t);

phi = @(x)[ones(1, length(x)); x; x.^2; x.^3; x.^4];
y_t_aprox = mcmmp_discreta(x, y, phi, t);


subplot(2,2,1)
plot(t, y_t_aprox, '-', t, yy, '-');
legend('aproximarea','functia');
legend('Location','southeast')
title('Varianta 1')

subplot(2,2,2)
plot(t,abs(yy-y_t_aprox),'-')
legend('eroarea');
title('eroarea')

phi = @(x) [get_cebisev_1_poly(x); x; x.^2; x.^3];
y_t_aprox = mcmmp_discreta(x, y, phi, t);

subplot(2,2,3)
plot(t, y_t_aprox, '-', t, yy, '-');
legend('aproximarea','functia');
legend('Location','southeast')
title('Varianta 2')

subplot(2,2,4)
plot(t,abs(yy-y_t_aprox),'-')
legend('eroarea');
title('eroarea')
