function Loto()
    b = [1,2,3,4,5];
    n=10000;
    nr =zeros(1,7);
    for i=1:n
       bilete=randsample(1:49,6,false);
       x=sum(ismember(b,bilete));
       if(x)>0
           nr(x)=nr(x)+1 ;
       end
    end
    for i=1:6
        fprintf("Prob %d: %4.2f\n",i,nr(i)/n);
    end
end