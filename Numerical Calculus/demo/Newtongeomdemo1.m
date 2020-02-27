%NEWTONGEOMDEMO1 - demonstratie cu Newton -geometric
clf
f=@(x) x.^3-x-1;
df=@(x) 3*x.^2-1;
a=1.1; b=2; n=3;
x0=1.9;
newtongeom(x0,f,df,a,b,n)