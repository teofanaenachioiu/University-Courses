%dobanzi Goldberg
n=365;
i=0.06; %rata anuala a dobanzii 6%
s=100;
sf=s*((1+i/n)^n-1)/(i/n)
ns=single(n); is=single(i);
ss=single(s);
sfs=ss*((1+i/n)^n-1)/(i/n)