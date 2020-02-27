function fixpointgeom(x0,phi,a,b,n)
xg=linspace(a,b,100);
yg=phi(xg);
plot(xg,yg,'k-',xg,xg,'--')
hold on
x1=phi(x0);
plot([x0,x1],[x1,x1]); plot(x0,x1,'s')
k=0;
s=sprintf('x_%d',k);
text(x0,x1,s,'VerticalAlignment','bottom','HorizontalAlignment','center');
x0=x1;
for k=1:n-1
    x1=phi(x0);
    plot([x0,x0,x1],[x0,x1,x1]); plot(x0,x1,'s');
    s=sprintf('x_%d',k);
    text(x0,x1,s,'VerticalAlignment','bottom','HorizontalAlignment','center');
    x0=x1;
end
x1=phi(x0);
plot([x0,x0],[x0,x1]); plot(x0,x1,'s');
s=sprintf('x_%d',k+1);
text(x0,x1,s,'VerticalAlignment','bottom','HorizontalAlignment','center');
%text(x0,phi(x0),'ult')
hold off