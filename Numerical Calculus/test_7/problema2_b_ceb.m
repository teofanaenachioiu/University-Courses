% Sa se aproximeze functia f:[-pi,pi]->R, f(x)=x+sin(x^2),
% cu o precizie de 1e-5.

err = 10^-5;
f=@(t) t+sin(t.^2);
df=@(t) 2.*t.*cos(t.^2) + 1;
a = -pi;
b = pi;

% metoda Hermite cu noduri duble (noduri cebisev de speta 1)

xi = linspace(a, b, 100000);
yi = f(xi);
ydi = df(xi);

m = 1;

while 1
  x = get_nodes_cebisev_1(m,a,b);
  pxi =  Hermite(x, f(x), df(x), xi);
  if max(abs(pxi - yi)) < err
      break;
  end
  m=m+1;
end

disp("noduri cebsev de speta 1 necesare");
m

plot(xi, abs(yi-pxi), '.') 