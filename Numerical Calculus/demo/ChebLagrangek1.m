function ff=ChebLagrangek1(y,xx,a,b)
%CHEBLAGRANGE - Lagrange interpolation for Chebyshev #1 points- barycentric
%call ff=ChebLagrangek1(y,xx,a,b)
%y - function values;
%xx - evaluation points
%a,b - interval
%ff - values of Lagrange interpolation polynomial

n = length(y)-1;
if nargin==2
    a=-1; b=1;
end
c = sin((2*(0:n)'+1)*pi/(2*n+2)).*(-1).^((0:n)');
x = sort(cos((2*(0:n)'+1)*pi/(2*n+2))*(b-a)/2+(a+b)/2);
numer = zeros(size(xx));
denom = zeros(size(xx));
exact = zeros(size(xx));
for j=1:n+1
    xdiff = xx-x(j);
    temp = c(j)./xdiff;
    numer = numer+temp*y(j);
    denom = denom+temp;
    exact(xdiff==0) = j;
end
ff = numer ./ denom;
jj = find(exact); 
ff(jj) = y(exact(jj));