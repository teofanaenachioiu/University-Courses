function [y,fr]=Bino(n,p)
    y=zeros(1,n);
    fr=zeros(1,2);
    for i=1:n
       y(i)=Bern(p);
    end
    c=sum(ismember(y,1));
    fr(1)=c/n;
    fr(2)=1-c/n;
    hist(y);
end