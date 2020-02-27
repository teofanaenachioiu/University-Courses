function Newtondemo(f,df,x0,a,b,nit)
clf
x=linspace(a,b,100);
y=f(x);
h=plot([a,b],[0,0],x,y);
hold on
xn=x0-f(x0)/df(x0);
h1=plot([x0,xn],[f(x0),0]);
x0=xn;
pause
for k=2:nit
    xn=x0-f(x0)/df(x0);
    set(h1,'xdata',[x0,xn],'ydata',[f(x0),0]);
    drawnow
    x0=xn;
    pause
end
hold off
