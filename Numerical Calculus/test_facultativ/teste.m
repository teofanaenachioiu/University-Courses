f = @(x) 25.*x.^2-10.*x+1;
format long
x0 = 0.19;
x1 = 0.21 ;

ea = 1e-8;

[z,ni] = secant(f,x0,x1,ea)