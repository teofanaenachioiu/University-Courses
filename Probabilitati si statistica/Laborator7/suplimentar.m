function suplimentar()
    X=[7 7 4 5 9 9 ...
        4 12 8 1 8 7 ...
        3 13 2 1 17 7 ...
        12 5 6 2 1 13 ...
        14 10 2 4 9 11 ...
        3 5 12 6 10 7];
    %H0: miu(0)=9
    %H1: miu(0)<9
    sigma=5;
    alfa=0.05;
    alfa1=0.01;
    
    lung=length(X);
    MX=mean(X);
    
    [H,P,CI,ZVAL]=ztest(X,9,sigma,alfa, -1) 
    
    if H==0
        fprintf('Ipoteza acceptata\n')
    else
        fprintf('Ipoteza respinsa\n')
    end
    sum(X)
end