function ff=baryLagrange(x,y,xx)
%BARYLAGRANGE - barycentric Lagrange interpolation
%call ff=baryLagrange(x,y,xi)
%x -  nodes
%y - function values
%xx - interpolation points
%ff - values of interpolation polynomial


%compute weights
n=length(x)-1;
c=ones(1,n+1);
for j=1:n+1
    c(j)=prod(x(j)-x([1:j-1,j+1:n+1]));
end
c=1./c;
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