function [x]=exponential(a,n)
    u=rand(1,n);
    x=(-1*log(1-u))/a;
    
end