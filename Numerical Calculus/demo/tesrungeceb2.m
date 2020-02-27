%testrungeceb2
f=@(x) 1./(1+x.^2);
k=0:n;
xn=5*sort(cos(k*pi/n));
yn=f(xn);
xg=5*[-1:0.04:1];
yg=f(xg);
ta=5*(-1:0.01:1);
ya=lagr(xn,yn,ta);
plot(xg,yg,'--',ta,ya,xn,yn,'o');
