function [x,y]=DNormala(a,n)
    u=rand(1,n);
    v=rand(1,n);
    x=sqrt(-2*log(u)).*cos(2*pi*v);
    y=sqrt(-2*log(u)).*sin(2*pi*v);
    hist(x)
    hist(y)
end