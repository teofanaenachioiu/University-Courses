function int=MonteCarlo2(a,b,n,fct)
    X=unifrnd(a,b,1,n);
    M=max(fct(X));
    Y=unifrnd(0,M,1,n); 
    int=M*(b-a)*mean(Y<=fct(X));
end