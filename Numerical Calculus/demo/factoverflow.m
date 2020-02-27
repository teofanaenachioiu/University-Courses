e=exp(1); y=0; n=0;
while y<=0
    n=n+1;
    y=(n/e)*log(n/e)-(log(realmax) ...
        -log(sqrt(2*pi*n)))/e;
end
n