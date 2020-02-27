%NEWTONGEOMDEMO1 - demonstratie cu Newton -geometric
clf

f=@(x) x.^3-x.^2-1;
df=@(x) 3*x.^2-2*x;
x0=2;
a=1; b=2.2; n=2;
newtongeom2(x0,f,df,a,b,n)