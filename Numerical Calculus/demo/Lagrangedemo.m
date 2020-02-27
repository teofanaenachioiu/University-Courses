%Lagrange demo
f=@(x) cos(2*x);
x=[0,pi/6,pi/4,pi/2,pi];
y=f(x);
t=linspace(0,pi,200);
z=lagr(x,y,t);
plot(x,y,'o',t,f(t),t,z)
legend('noduri','f','L_mf','Location','best')