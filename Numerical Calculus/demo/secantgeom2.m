function secantgeom2(x0,x1,phi,a,b,n)
clf
xg=linspace(a,b,100); deplx=0.025; depl=0.2;
yg=phi(xg);
z=fzero(phi,[a,b]);
plot(xg,yg,'k-',[a,b],[0,0],'b-')
hold on
plot([x0,x1],[0,0],'o',[x0,x0],[0,phi(x0)],'b--o',[x1,x1],[0,phi(x1)],'b--o');
text(x0-deplx,0,'$x_0$','VerticalAlignment','middle','HorizontalAlignment','right',...
    'Interpreter','LaTEX','Fontsize',20,'FontName','TimesNewRoman');
text(x0-deplx,phi(x0)/2,'$f(x_0)$','VerticalAlignment','middle','HorizontalAlignment','right',...
    'Interpreter','LaTEX','Fontsize',20,'FontName','TimesNewRoman');
text(x1+deplx,0,'$x_1$','VerticalAlignment','top','HorizontalAlignment','left',...
    'Interpreter','LaTEX','Fontsize',20,'FontName','TimesNewRoman');
text(x1+deplx,phi(x1)/2,'$f(x_1)$','VerticalAlignment','top','HorizontalAlignment','left',...
    'Interpreter','LaTEX','Fontsize',20,'FontName','TimesNewRoman');
for k=1:n
    x2=x1-phi(x1)*(x1-x0)/(phi(x1)-phi(x0));
    xg=[x0,x1,x2]; yg=[phi(x0),phi(x1),0];
    [xg,ii]=sort(xg); yg=yg(ii);
    plot(xg,yg,'k--'); plot(x2,0,'o'); plot([x2,x2],[0,phi(x2)],'b--');
    s=sprintf('$x_%d$',k+1);
    text(x2,0-depl,s,'VerticalAlignment','top','HorizontalAlignment','center',...
    'Interpreter','LaTEX','Fontsize',20,'FontName','TimesNewRoman');
    x0=x1;
    x1=x2;
end
text(z,0-depl,'$\alpha$','VerticalAlignment','top','HorizontalAlignment','center',...
    'Interpreter','LaTEX','Fontsize',20,'FontName','TimesNewRoman');
%s=sprintf('x_%d',k);
%text(x0,0,s,'VerticalAlignment','top','HorizontalAlignment','center');
hold off