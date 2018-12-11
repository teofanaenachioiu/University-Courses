function zar()
    n=10; %zar / joc
     
    N=100; %nr jocuri
    x=zeros(1,N);
    
    for i=1:N
        scor=0;
        for j=1:n
            p=randperm(6,1);
            if p==1
               scor=scor+2; 
            end
            if p>=2 || p<=5
                scor=scor-1;
            end 
        end
        x(i)=scor;
    end
    m=mean(x)
    v=var(x)
    hist(x);
end