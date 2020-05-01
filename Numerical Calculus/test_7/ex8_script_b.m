a = -2*pi;
b = 2*pi;
x = linspace(a, b, 200);
f = @(x) x.^2 .* sin(x);
df = @(x) 2*x.*sin(x) + x.^2 .* cos(x);
y = f(x);
dy = df(x);
err = 1e-6;


m_che = 1;
xx = get_nodes_cebisev_1( m_che,a, b);
yy_che = Hermite(xx, f(xx), df(xx), x);

while max(abs(y - yy_che)) > err
  m_che =m_che+ 1;
  xx = get_nodes_cebisev_1( m_che,a,b);
  yy_che = Hermite(xx, f(xx), df(xx), x);
end

m_che