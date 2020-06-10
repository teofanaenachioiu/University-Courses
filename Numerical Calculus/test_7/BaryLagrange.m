function pxi=BaryLagrange(x,y,xi,c)
%x -  nodurile
%y - valorile functiei in noduri
%xi - punctele de interpolare
%pxi - valorile polinomului de interpolare

n = length(x);
numarator = zeros(size(xi));
numitor = zeros(size(xi));
exact = zeros(size(xi));
for j=1:n
    xdiff = xi-x(j);
    temp = c(j)./xdiff;
    numarator = numarator+temp*y(j);
    numitor = numitor+temp;
    exact(xdiff==0) = j;
end
pxi = numarator ./ numitor;
jj = find(exact); 
pxi(jj) = y(exact(jj));