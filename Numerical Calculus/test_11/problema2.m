ea = 0;
er = 1e-3;

x0 = [10, 1, -6]';

f = @(x) [x(1)*exp(x(2))+x(3)-10;
   x(1)*exp(2*x(2))+2*x(3)-12; 
   x(1)*exp(3*x(2))+3*x(3)-18];

fjacob = @(x) [exp(x(2)), x(1)*exp(x(2)), 1;
           exp(2*x(2)), 2*x(1)*exp(2*x(2)), 2;
           exp(3*x(2)), 3*x(1)*exp(3*x(2)), 3];

[z,ni] = Newton(f,fjacob,x0,ea,er);
alpha = z(1)
beta = z(2)
gamma = z(3)
