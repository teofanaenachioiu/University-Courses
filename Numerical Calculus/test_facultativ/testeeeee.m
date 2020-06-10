lambda = 1/2;


f = @(x) x.^(2-lambda)-a*x.^(-lambda);
%fd = @(x) a./(2.*x.^(3./2)) + (3.*x.^(1./2))./2;
fdd = @ (x) 3/(4*x^(1/2)) - (3*a)/(4*x^(5/2));
fdd(x)/f(x)
%x0 = 10;
%[z,ni] = Newton(f,fd,x0)
