function [x]=NBino(n,p)
    N=10;
    x=zeros(1,N);
    nrAp=0;
    for i=1:N
        for j=1:N
            u=Bern(p);
            if u==0
                x(i)=x(i)+1;
            else 
                nrAp=nrAp+1;
                if nrAp==n
                    break;
                end
            end
        end
    end
end