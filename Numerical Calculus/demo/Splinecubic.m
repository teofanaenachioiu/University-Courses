function [a,b,c,d]=Splinecubic(x,f,tip,der)
%SPLINECUBIC - determina coeficientii spline-ului cubic
%apel [a,b,c,d]=Splinecubic(x,f,tip,der)
%x - abscisele
%f - ordonatele
%tip - 0  complet
%      1  cu derivate secunde
%      2  natural
%      3  not a knot (deBoor)
%der - informatii despre derivate 
%      [f'(a),f'(b)] pentru tipul 0
%      [f''(a), f''(b)] pentru tipul 4

if (nargin<4) | (tip==2), der=[0,0]; end

n=length(x);
if any(diff(x)<0), [x,ind]=sort(x); else ind=1:n; end
y=f(ind); x=x(:); y=y(:);
dx=diff(x);  ddiv=diff(y)./dx;
ds=dx(1:end-1); dd=dx(2:end);
dp=2*(ds+dd);
md=3*(dd.*ddiv(1:end-1)+ds.*ddiv(2:end));
switch tip
case 0 %complet
    dp1=1; dpn=1; vd1=0; vdn=0;
    md1=der(1); mdn=der(2);
case {1,2}
    dp1=2; dpn=2; vd1=1; vdn=1;
    md1=3*ddiv(1)-0.5*dx(1)*der(1);
    mdn=3*ddiv(end)+0.5*dx(end)*der(2);
case 3
    x31=x(3)-x(1);xn=x(n)-x(n-2);
    dp1=dx(2); dpn=dx(end-1);
    vd1=x31;
    vdn=xn;
    md1=((dx(1)+2*x31)*dx(2)*ddiv(1)+dx(1)^2*ddiv(2))/x31;
    mdn=(dx(end)^2*ddiv(end-1)+(2*xn+dx(end))*dx(end-1)*ddiv(end))/xn;
end
dp=[dp1;dp;dpn];
dp1=[0;vd1;dd];
dm1=[ds;vdn;0];
md=[md1;md;mdn];
A=spdiags([dm1,dp,dp1],-1:1,n,n);
m=A\md;
d=y(1:end-1);
c=m(1:end-1);
a=(m(2:end)+m(1:end-1)-2*ddiv)./(dx.^2);
b=(ddiv-m(1:end-1))./dx-dx.*a;

