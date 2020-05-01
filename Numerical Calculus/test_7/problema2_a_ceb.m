% Sa se aproximeze functia f:[-pi,pi]->R, f(x)=x+sin(x^2),
% cu o precizie de 1e-5.


err = 10^-5;
f=@(t) t+sin(t.^2);
a = -pi;
b = pi;
x = linspace(a, b, 1000);
y = f(x);

% metoda baricentrica (noduri echidistante)

m = 1;

while 1  
    nodes = get_nodes_cebisev_1(m,a,b);
    weights = get_weights_cebisev_1(m);
    px = BaryLagrange(nodes,f,x,weights);
    if max(abs(px-y)) < err
        break;
    end
    m = m+1;
end

disp("noduri cebsev de speta 1 necesare");
m

