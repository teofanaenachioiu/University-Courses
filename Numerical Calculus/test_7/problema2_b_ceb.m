% Sa se aproximeze functia f:[-pi,pi]->R, f(x)=x+sin(x^2),
% cu o precizie de 1e-5.

syms t
err = 10^-5;
f=@(t) t+sin(t.^2);
df=@(t) 2.*t.*cos(t.^2) + 1;
a = -pi;
b = pi;
x = linspace(a, b, 1000);
y = f(x);
yd = df(x);

% metoda Hermite cu noduri duble (noduri cebisev de speta 1)

m = 1;

while 1
  nodes = get_nodes_cebisev_1(m,a,b);
  yy =  Hermite(nodes, f(nodes), df(nodes), x);
  if max(abs(yy - y)) < err
      break;
  end
  m=m+1;
end

disp("noduri cebsev de speta 1 necesare");
m

