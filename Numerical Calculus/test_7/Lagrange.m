function lambda=Lagrange(node, t)
% determinam functia lambda
% node - nodurile
% t - punctele in care aproximam functia 

[mu,nu]=size(t);
lambda=zeros(mu,nu);
m=length(node);
for i=1:m
    z=ones(mu,nu);
    for j=[1:i-1,i+1:m]
        z=z.*(t-node(j))/(node(i)-node(j));
    end
    lambda=lambda+abs(z);
end
