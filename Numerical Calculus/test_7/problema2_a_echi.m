% Sa se aproximeze functia f:[-pi,pi]->R, f(x)=x+sin(x^2),
% cu o precizie de 1e-5.


err = 10^-5;
f=@(t) t+sin(t.^2);
a = -pi;
b = pi;
x = linspace(a, b, 200);
y = f(x);

% metoda baricentrica (noduri echidistante)

m = 1;
nodes = linspace(a, b, m);
weights = get_weights_echidist(m);
px = BaryLagrange(nodes, f, x, weights);

while max(abs(px - y)) > err
    m = m+1;
    nodes = linspace(a, b, m);
    weights = get_weights_echidist(m);
    px = BaryLagrange(nodes, f, x, weights);
end
disp("noduri echidistante necesare");
m
