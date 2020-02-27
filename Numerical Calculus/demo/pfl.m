function  Z=pfl(x,t)
%PFL compute basic Lagrange polynomials
%x - interpolation points
%t - evaluation points
%Z - results nodes-line pts - column

m=length(x);
n=length(t);
TT=zeros(m,n);
Z=TT;
XX=zeros(m,m);
for i=1:m
    TT(i,:)=t-x(i);
end
for i=1:m
    XX(i,:)=x-x(i);
end
for j=1:m
  TX(j)=prod(XX([1:j-1,j+1:m],j));
end
for i=1:m
   Z(i,:)=prod(TT([1:i-1,i+1:m],:))/TX(i);
end
  
