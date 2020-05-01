% Sa se aproximeze functia f:[-pi,pi]->R, f(x)=x+sin(x^2),
% cu o precizie de 1e-5.

syms t
err = 10^-5;
f=@(t) t+sin(t.^2);
df=@(t) 2.*t.*cos(t.^2) + 1;
a = -pi;
b = pi;
x = linspace(a, b, 200);
y = f(x);
yd = df(x);

% metoda Hermite cu noduri duble (noduri echidistante)

m = 1;
nodes = linspace(a, b, m);
yy =  Hermite(nodes, f(nodes), df(nodes), x);

while max(abs(yy - y)) > err
     m = m+1;
     nodes = linspace(a, b, m);
     yy =  Hermite(nodes, f(nodes), df(nodes), x);
end
disp("noduri echidistante necesare");
m


