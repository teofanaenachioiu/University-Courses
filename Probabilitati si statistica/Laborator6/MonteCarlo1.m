function int=MonteCarlo1(a, b,n,fct)
        u=unifrnd(a,b,1,n);
        X=fct(u);
        xm=mean(X);
        int=(b-a)*xm;
end