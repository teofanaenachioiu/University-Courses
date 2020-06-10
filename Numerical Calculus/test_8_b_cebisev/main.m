% 3. Sa se aproximeze functia f(x)=arctan x, pe [-1,1];
% (b) Folosind metoda celor mai mici patrate discreta si 
% noduri echidistante, n = 10.

a = -1;
b = 1;
f = @(x) atan(x);
m = 15;

x = linspace(a, b, m); % noduri echidistante
y = f(x);

% determin coeficientii cebisev de speta 1
c = get_cebisev_coef(f,20);
% aproximanta 
y_aprox = get_aprox_cebisev(c,x);


subplot(2,1,1)
plot(x, y_aprox, '-', x, y, '-');
legend('aproximarea','functia');
legend('Location','southeast')
title('Aproximare folosind polinoame cebisev')

subplot(2,1,2)
plot(x,abs(y-y_aprox),'-')
legend('eroarea');
legend('Location','southeast')
title('eroarea')