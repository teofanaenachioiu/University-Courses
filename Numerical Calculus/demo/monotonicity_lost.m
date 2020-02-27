%MONOTONICITY_LOST
%is monotonicity lost or instability
f=@(x) x.^5-5*x.^4+10*x.^3-10*x.^2+5*x-1;
x=linspace(0.999,1.001,200);
plot(x,f(x),x,(x-1).^5)
p=[1,-5,10,-10,5,-1];
plot(x,f(x),x,(x-1).^5,x,polyval(p,x))
legend('f expanded','(x-1)^5','Horner','Location','best');