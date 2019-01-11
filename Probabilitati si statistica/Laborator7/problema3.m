function tevi()
    %H0: v<=4
    %H1: v>4
    s=unifrnd(1,4);
    n=100;
    X=normrnd(400,s,1,n);
    alfa=0.01;
    [H,P,CI,STATUS]=vartest(X,var(X),alfa,1)
end