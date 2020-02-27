function newtongeom2(x0,phi,phid,a,b,n)
xg=linspace(a,b,100); deplx=0.025; depl=0.95;
yg=phi(xg);
z=fzero(phi,b);
plot(xg,yg,'k-','Linewidth',1.5);
hold on
plot([a,b],[0,0],'b-')
plot([x0,z],[0,0],'s'); plot([x0,x0],[0,phi(x0)],'k:');
yl=ylim;
depl=(yl(2)-yl(1))/20;
for k=1:n
    x1=x0-phi(x0)/phid(x0);
    plot([x0,x1],[phi(x0),0],'k-'); plot(x1,0,'s');
    plot([x1,x1],[0,phi(x1)],'k:')
    s=sprintf('$x_%d$',k-1);
    text(x0,0-depl,s,'VerticalAlignment','top','HorizontalAlignment','center',...
    'Interpreter','LaTEX','Fontsize',20,'FontName','TimesNewRoman');
    x0=x1;
end
s=sprintf('$x_%d$',k);
text(x0,0-depl,s,'VerticalAlignment','top','HorizontalAlignment','center',...
    'Interpreter','LaTEX','Fontsize',20,'FontName','TimesNewRoman');
text(z,0+depl,'$\alpha$','VerticalAlignment','bottom','HorizontalAlignment','center',...
    'Interpreter','LaTEX','Fontsize',20,'FontName','TimesNewRoman');

hold off