function [x]=Poisson(a,n)
    x=zeros(1,n);
    for i=1:n
        k=0;
        t=0;
        while 1
            k=k+1;
            t=t+exponential(a,1);
            if t>1
                break;
            end
        end
        x(i)=k-1;
    end
    hist(x)
    
end