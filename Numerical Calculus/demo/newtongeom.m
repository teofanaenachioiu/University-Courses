function Newtongeom(x0,phi,phid,a,b,n)
xg=linspace(a,b,100);
yg=phi(xg);
plot(xg,yg,'k-',[a,b],[0,0],'b--')
hold on
plot(x0,0,'s'); plot([x0,x0],[0,phi(x0)],'k--');
for k=1:n
    x1=x0-phi(x0)/phid(x0);
    plot([x0,x1],[phi(x0),0],'k-'); plot(x1,0,'s');
    plot([x1,x1],[0,phi(x1)],'k--')
    s=sprintf('x_%d',k-1);
    text(x0,0,s,'VerticalAlignment','top','HorizontalAlignment','center');
    x0=x1;
end
s=sprintf('x_%d',k);
text(x0,0,s,'VerticalAlignment','top','HorizontalAlignment','center');
hold off