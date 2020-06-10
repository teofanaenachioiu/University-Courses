% Sa se aproximeze functia f:[-pi,pi]->R, f(x)=x+sin(x^2),
% cu o precizie de 1e-5.

err = 10^-5;
f=@(t) t+sin(t.^2);
a = -pi;
b = pi;

% metoda baricentrica (noduri echidistante)

xi = linspace(a, b, 100000);
yi = f(xi);
m = 1;

while 1
    x = get_nodes_echi(m,a,b);
    y = f(x);
    w = get_weights_bary(x);
    pxi = BaryLagrange(x,y,xi,w);
    if max(abs(pxi - yi)) < err
        break;
    end
    m = m+1;
end

disp("noduri echidistante necesare");
m

plot(xi, abs(yi-pxi), '.') 