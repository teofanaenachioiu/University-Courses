function fi=lagr(x,y,xi)
%LAGR - calculeaza polinomul de interpolare Lagrange
% x,y -coordonatele nodurilor
% xi - punctele in care se evalueaza polinomul

if nargin ~=3 
    error('numar ilegal de argumente')
end
[mu,nu]=size(xi);
fi=zeros(mu,nu);
np1=length(y);
for i=1:np1
    z=ones(mu,nu);
    for j=[1:i-1,i+1:np1]
        z=z.*(xi-x(j))/(x(i)-x(j));
    end;
    fi=fi+z*y(i);
end
