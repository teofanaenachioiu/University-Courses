n=2^52;
f=1;
for k = -52:44
    f=f+2^k;
    n=n*f;
    e=(1+1/n)^n;
    fprintf('%20.18f %18d %d\n',e,n,k);
    if e==1, break; end
end
