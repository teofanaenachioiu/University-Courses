function [I,nfev]=Rombergdemo(f,a,b,epsi,nmax)
%ROMBERG - approximate an integral using Romberg method
%call [I,NFEV]=ROMBERG(F,A,B,EPSI,NMAX)
%F -integrands
%A,B - integration limits
%EPSI - tolerance
%NMAX - maximum number of iterations
%I - approximate integral value
%NFEV - number of function evaluations
clf
axis([0,12,0,11]);
% xx=xlim;
% ll=(xx(2)-xx(1))/11;
if nargin < 5
  nmax=10;
end
if nargin < 4
  epsi=1e-3;
end
R=zeros(nmax,nmax);
h=b-a;
% first iteration

R(1,1)=h/2*(sum(f([a,b])));
sr=sprintf('%7.5f',R(1,1));
ht=text(0,10,sr,'HorizontalAlignment','left','FontSize',8,'VerticalAlignment','middle','Color','red');
axis off
shg
pause
nfev=2;
for k=2:nmax
   %trapezes formula 
   x=a+([1:2^(k-2)]-0.5)*h;
   R(k,1)=0.5*(R(k-1,1)+h*sum(f(x)));
   sr=sprintf('%7.5f',R(k,1));
   set(ht,'Color','black');
   ht=text(0,11-k,sr,'HorizontalAlignment','left','FontSize',8,'VerticalAlignment','middle','Color','red');
   nfev=nfev+length(x);
   %extrapolation
   plj=4;
   pause
   for j=2:k
      R(k,j)=(plj*R(k,j-1)-R(k-1,j-1))/(plj-1);
      sr=sprintf('%7.5f',R(k,j));
      set(ht,'Color','black');
      ht=text((j-1)*1.2,11-k,sr,'HorizontalAlignment','left','FontSize',8,'VerticalAlignment','middle','Color','red');
      plj=plj*4;
      pause
   end
   if (abs(R(k,k)-R(k-1,k-1))<epsi)&&(k>3)
      I=R(k,k);
      title(sprintf('Q = %8.4f, nfev = %4d',I,nfev))
      %set(q,'string','close','callback','close(gcf)')
      return
   end
   %halving step
   h=h/2;
end
error('iteration number exceeded')
