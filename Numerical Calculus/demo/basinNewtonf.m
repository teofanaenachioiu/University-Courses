function  basinNewtonf(f,fd,a,b,n,m)
%basinNewton - basin of attraction for Newton method
%call basinNewtonf(f,fd,a,b,n,m)
% f, fd - function and its derivative
% a,b - interval
% n #points
% m #iterations
if nargin < 6, m=30; end
if nargin < 5, n=1000; end
if nargin < 4, b=1; end
if nargin < 3, a=-1; end

x=a:2*(b-a)/n:b;
[X,Y]=meshgrid(x,x);
Z=X+1i*Y;
for i=1:m
    Z=Z-f(Z)./fd(Z);
end
image((round(imag(Z))+2)*10);