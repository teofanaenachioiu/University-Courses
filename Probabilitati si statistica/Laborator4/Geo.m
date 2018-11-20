function [x]=Geo(p)
    N=10;
    x=zeros(1,N);
    for i=1:N
        x(i)=Bern(p);
    end
end