f1scal = @(t,y) -y+5*exp(-t).*cos(5*t);
tspan = [0,3]; yzero=0;
[t,y] = ode45(f1scal,tspan,yzero);
plot(t,y,'k--*')
xlabel('t'), ylabel('y(t)')
norm(y-exp(-t).*sin(5*t),inf)