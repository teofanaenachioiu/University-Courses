%secantdemogr
phi = @(x) x.^3-x.^2-1;
a = 0; b=2;
x0 = 0.5; x1=2;
n=4;
figure(1)
secantgeom(x0,x1,phi,a,b,n)
figure(2)
secantgeom2(x0,x1,phi,a,b,n)