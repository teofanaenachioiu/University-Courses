%function runge4(n)
%test convergenta interpolare Lagrange pentru noduri Cebisev
f=@(x) 1./(1+x.^2);
k=1:n;
xn=5*sort(cos((2*k-1)*pi/2/n));
yn=f(xn);
xg=5*[-1:0.01:1];
yg=f(xg);
ta=5*[-1:0.005:1];
ya=lagr(xn,yn,ta);
plot(xg,yg,'--',ta,ya,xn,yn,'o');
