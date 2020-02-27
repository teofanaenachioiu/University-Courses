function [Q,fcount] = quadgui(F,a,b,tol,varargin)
%QUADGUI  Demonstrate numerical evaluation of a definite integral.
%   Q = QUADGUI(F,A,B) shows the steps in approximating the integral
%   of F(x) from A to B by adaptive extrapolated Simpson's quadrature.
%
%   The shaded area shows the integral over the current subinterval.
%   The color switches to green when the desired accuracy is obtained.
%
%   Q = QUADGUI(F,A,B,tol) uses the given tolerance instead of 1.e-4.
%
%   F is a string defining a function of a single variable, an inline
%   function, a function handle, or a symbolic expression involving a
%   single variable.  Arguments to QUADGUI beyond the first four,
%   Q = QUADGUI(F,a,b,tol,p1,p2,...), are passed on to the integrand,
%   F(x,p1,p2,..).
%
%   [Q,fcount] = QUADGUI(F,...) also counts the number of evaluations
%   of F(x).
%   
%   Examples:
%      quadgui(@humps,0,1)
%      quadgui(@humps,0,1,1.e-6)
%      quadgui(@humps,-1,2)
%      quadgui('sin(x)',0,pi,1.e-8)
%      quadgui('cos(x)',0,(9/2)*pi,1.e-6)
%      quadgui('sqrt(x)',0,1,1.e-8)
%      quadgui('-sqrt(x)*log(x)',eps,1,1.e-8)
%      quadgui('1/(3*x-1)',0,1)
%      quadgui('t^(8/3)*(1-t)^(10/3)',0,1,1.e-8)
%      betafun = inline('t^p*(1-t)^q','t','p','q')
%      quadgui(betafun,0,1,1.e-10,15,2)
%
%   See also QUADTX, QUAD, QUADL, DBLQUAD.

shg
clf reset
set(gcf,'doublebuffer','on','menubar','none', ...
   'numbertitle','off','name','Quad gui')

% Default tolerance
if nargin < 4 | isempty(tol)
   tol = 1.e-4;
end

% Default function and interval.
if nargin < 3
   F = @humps;
   a = 0;
   b = 1;
end

% Make F callable by feval.
if ischar(F) & exist(F)~=2
   F = inline(F);
elseif isa(F,'sym')
   F = inline(char(F));
end 

% Initialization
c = (a + b)/2;
fa = feval(F,a,varargin{:});
fc = feval(F,c,varargin{:});
fb = feval(F,b,varargin{:});

% Scale the plot
h = b - a;
x = [a c b];
y = [fa fc fb];
maxy = max(y);
miny = min(y);
for k = 1:63
   v = feval(F,a+k*h/64,varargin{:});
   maxy = real(max(maxy,v));
   miny = real(min(miny,v));
end
set(gcf,'userdata',0)
plot(x,y,'.','markersize',6);
hold on
p(1) = fill(a,fa,'k');
p(2) = fill(b,fb,'k');
hold off
s = (maxy - miny)/20;
axis([a b miny-s maxy+s])
q(1) = uicontrol('string','step', ...
   'units','normal','pos',[.65 .02 .08 .06], ...
   'callback','set(gcf,''userdata'',1)');
q(2) = uicontrol('string','auto', ...
   'units','normal','pos',[.75 .02 .08 .06], ...
   'callback','set(gcf,''userdata'',2)');
q(3) = uicontrol('string','quit', ...
   'units','normal','pos',[.85 .02 .08 .06], ...
   'callback','set(gcf,''userdata'',3)');

% Recursive call 
[Q,k] = quadguistep(F, a, b, tol, fa, fc, fb, varargin{:});
fcount = k + 3;

% Finish
title(sprintf('Q = %8.4f, fcount = %4.0f',Q,fcount))
delete(p);
delete(q(1:2));
set(q(3),'string','close','callback','close(gcf)')


% ---------------------------------------------------------

function [Q,fcount] = quadguistep(F,a,b,tol,fa,fc,fb,varargin)

% Recursive subfunction used by quadtx.

h = b - a; 
c = (a + b)/2;
d = (a + c)/2;
e = (c + b)/2;
fd = feval(F,d,varargin{:});
fe = feval(F,e,varargin{:});
Q1 = h/6 * (fa + 4*fc + fb);
Q2 = h/12 * (fa + 4*fd + 2*fc + 4*fe + fb);

u1 = a:h/64:c;
v1 = polyinterp([a d c],[fa fd fc],u1);
u1 = [a u1 c];
v1 = [0 v1 0];
u2 = c:h/64:b;
v2 = polyinterp([c e b],[fc fe fb],u2);
u2 = [c u2 b];
v2 = [0 v2 0];
if (abs(Q2 - Q1) <= tol)
   color = [0 2/3 0];
else
   color = [.6 .6 .6];
end
p = flipud(get(gca,'child'));
x = [get(p(1),'xdata') d e];
y = [get(p(1),'ydata') fd fe];
set(p(1),'xdata',x,'ydata',y)
set(p(2),'xdata',u1,'ydata',v1,'facecolor',color)
set(p(3),'xdata',u2,'ydata',v2,'facecolor',color)
set(gca,'xtick',sort(x),'xticklabel',[]);
title(num2str(length(x)))
pause(.25)
while get(gcf,'userdata') == 0
   pause(.25)
end
if get(gcf,'userdata') == 1
   set(gcf,'userdata',0)
end

if (abs(Q2 - Q1) <= tol) | (get(gcf,'userdata') == 3)
   Q  = Q2 + (Q2 - Q1)/15;
   fcount = 2;
else
   [Qa,ka] = quadguistep(F, a, c, tol, fa, fd, fc, varargin{:});
   [Qb,kb] = quadguistep(F, c, b, tol, fc, fe, fb, varargin{:});
   Q  = Qa + Qb;
   fcount = ka + kb + 2;
end
