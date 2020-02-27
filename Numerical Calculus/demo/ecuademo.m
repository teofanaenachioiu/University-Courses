
f = @(x) x.^3-2*x.^2+x-3;
df = @(x) 3*x.^2-4*x+1;
[z1,ni1]=Newton(f,df,0,1e-10)
[z2,ni2]=Newton(f,df,3,1e-10)
[z3,ni3]=secant(f,0,3,1e-10)