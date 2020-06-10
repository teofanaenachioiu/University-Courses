% Sa se aproximeze functia f:[-pi,pi]->R, f(x)=x+sin(x^2),
% cu o precizie de 1e-5.

syms t
err = 10^-5;
f=@(t) t+sin(t.^2);
df=@(t) 2.*t.*cos(t.^2) + 1;
a = -pi;
b = pi;

% metoda Hermite cu noduri duble (noduri echidistante)

xi = linspace(a, b, 10000);
yi = f(xi);
ydi = df(xi);

m = 1;

while 1
     x = linspace(a, b, m);
     pxi =  Hermite(x, f(x), df(x), xi);
     if max(abs(pxi - yi)) < err
         break;
     end
     m = m+1;
end

disp("noduri echidistante necesare");
m

plot(xi, abs(yi-pxi), '.') 
