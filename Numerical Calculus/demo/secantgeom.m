function secantgeom(x0,x1,phi,a,b,n)
clf
xg=linspace(a,b,100);
yg=phi(xg);
plot(xg,yg,'k-',[a,b],[0,0],'b--')
hold on
plot([x0,x1],[0,0],'s');
text(x0,0,'x_0','VerticalAlignment','top','HorizontalAlignment','center');
text(x1,0,'x_1','VerticalAlignment','top','HorizontalAlignment','center');
for k=1:n
    x2=x1-phi(x1)*(x1-x0)/(phi(x1)-phi(x0));
    xg=[x0,x1,x2]; yg=[phi(x0),phi(x1),0];
    [xg,ii]=sort(xg); yg=yg(ii);
    plot(xg,yg,'k--'); plot(x2,0,'s');
    s=sprintf('x_%d',k+1);
    text(x2,0,s,'VerticalAlignment','top','HorizontalAlignment','center');
    x0=x1;
    x1=x2;
end
%s=sprintf('x_%d',k);
%text(x0,0,s,'VerticalAlignment','top','HorizontalAlignment','center');
hold off