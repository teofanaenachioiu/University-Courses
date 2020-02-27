function p=vJacobi(n,x,al,be);
%vJacobi value of Jacobi polynomial
%call y=vJacobi(n,x,a,b);

k=0:n;
u=2*k+al+be;
a=(be^2-al^2)./(u.*(u+2));
b=4*k.*(k+al).*(k+al+be).*(k+be)./((u-1).*u.^2.*(u+1));
b(1)=2^(al+be+1)*beta(al+1,be+1);
if (u(1)==0), a(1)=0; end
pm1=zeros(size(x)); pm=ones(size(x));
if n==0 
    p=pm;
else
    for k=1:n
        p=(x-a(k)).*pm-b(k).*pm1;
        pm1=pm;
        pm=p;
    end
end