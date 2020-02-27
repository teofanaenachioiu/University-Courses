f0='%8.0f %7.0f\n';
disp('       x      n')
for x=10:10:100
    y=0; n=0;
    while y<=0
        n=n+1;
        y=n*log(x)-log(realmax);
    end
    fprintf(f0,x,n)
end