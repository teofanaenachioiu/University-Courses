function y=chirila(x)
t=x~=0;
y=ones(size(x));
y(t)=exp(x(t)-1)./x(t);