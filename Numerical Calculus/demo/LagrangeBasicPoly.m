function v=LagrangeBasicPoly(t,x,k)
%LAGRANGEBASICPOLY - basic Lagrange polynomial
%call v=LagrangeBasicPoly(t,x,k)
%t - evaluation points
%x - nodes
%k - index

k=k+1;
v=ones(size(t));
n=length(x);
for i=[1:k-1,k+1:n]
    v=v.*(t-x(i))/(x(k)-x(i));
end
