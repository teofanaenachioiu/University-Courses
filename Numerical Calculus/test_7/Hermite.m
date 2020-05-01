function ff = Hermite(nodes, y, dy, x)
% nodes - noduri de interpolare
% y - valoarea functiei in noduri
% dy - valoarea derivatei functiei in noduri
% x - puncte de aproximat
% ff - approximarea functiei
  
[z, Q] = difdiv(nodes, y, dy);

nx = length(x);
nX = length(z);
  
for i = 1:nx
    x_diff = x(i) - z;
    y(i) = [1, cumprod(x_diff(1:nX-1))]*Q';
end
ff = y;
end