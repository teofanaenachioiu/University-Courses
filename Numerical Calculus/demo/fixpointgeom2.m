function fixpointgeom2(x0,phi,a,b,n)
plot([a,b],[0,0],'k-')
hold on

xg=linspace(a,b,100); deplx=0.025; depl=0.05;
yg=phi(xg);

plot(xg,yg,'k-',xg,xg,'-')
a=axis;
plot([0,a(2)],[0,0],'k-');
plot([0,0],[0,a(4)],'k-');
plot(a(2),0,'k>','MarkerFaceColor','black');
plot(0,a(4),'k^','MarkerFaceColor','black');
x1=phi(x0);
plot([x0,x1],[x1,x1],'b:'); plot(x0,x1,'s')
k=0;
s=sprintf('$x_%d$',k);
text(x0,x1+depl,s,'VerticalAlignment','bottom','HorizontalAlignment','center',...
    'Interpreter','LaTEX','Fontsize',20,'FontName','TimesNewRoman');
x0=x1;
for k=1:n-1
    x1=phi(x0);
    plot([x0,x0,x1],[x0,x1,x1],'b:'); plot(x0,x1,'s');
    s=sprintf('$x_%d$',k);
    text(x0,x1+depl,s,'VerticalAlignment','bottom','HorizontalAlignment','center',...
    'Interpreter','LaTEX','Fontsize',20,'FontName','TimesNewRoman');
    x0=x1;
end
x1=phi(x0);
plot([x0,x0],[x0,x1],'b:'); plot(x0,x1,'s');
s=sprintf('$x_%d$',k+1);
text(x0,x1+depl,s,'VerticalAlignment','bottom','HorizontalAlignment','center',...
    'Interpreter','LaTEX','Fontsize',20,'FontName','TimesNewRoman');
%text(x0,phi(x0),'ult')
hold off