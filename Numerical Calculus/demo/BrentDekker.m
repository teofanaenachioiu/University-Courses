function b = BrentDekker(F,ab,varargin)
%BRENTDEKKER  Brent-Dekker algorithm.
%   x = BrentDekker(F,[a,b]) tries to find a zero of F(x) between a and b.
%   F(a) and F(b) must have opposite signs.  BrentDekker returns one 
%   end point of a small subinterval of [a,b] where F changes sign.
%   Arguments beyond the first two, Brent-Dekker(F,[a,b],p1,p2,...),
%   are passed on, F(x,p1,p2,..).
%
%   Example:
%      BrentDekker('sin(x)',[1,4])

% Make F callable by feval.
if ischar(F) & exist(F)~=2
   F = inline(F);
elseif isa(F,'sym')
   F = inline(char(F));
end 

% Initialize.
a = ab(1);
b = ab(2);
fa = F(a,varargin{:});
fb = F(b,varargin{:});
if sign(fa) == sign(fb)
   error('Function must change sign on the interval')
end
c = a;
fc = fa;
d = b - c;
e = d;

% Main loop, exit from middle of the loop
while fb ~= 0

   % The three current points, a, b, and c, satisfy:
   %    f(x) changes sign between a and b.
   %    abs(f(b)) <= abs(f(a)).
   %    c = previous b, so c might = a.
   % The next point is chosen from
   %    Bisection point, (a+b)/2.
   %    Secant point determined by b and c.
   %    Inverse quadratic interpolation point determined
   %    by a, b, and c if they are distinct.

   if sign(fa) == sign(fb)
      a = c;  fa = fc;
      d = b - c;  e = d;
   end
   if abs(fa) < abs(fb)
      c = b;    b = a;    a = c;
      fc = fb;  fb = fa;  fa = fc;
   end
   
   % Convergence test and possible exit
   m = 0.5*(a - b);
   tol = 2.0*eps*max(abs(b),1.0);
   if (abs(m) <= tol) | (fb == 0.0)
      break
   end
   
   % Choose bisection or interpolation
   if (abs(e) < tol) | (abs(fc) <= abs(fb))
      % Bisection
      d = m;
      e = m;
   else
      % Interpolation
      s = fb/fc;
      if (a == c)
         % Linear interpolation (secant)
         p = 2.0*m*s;
         q = 1.0 - s;
      else
         % Inverse quadratic interpolation
         q = fc/fa;
         r = fb/fa;
         p = s*(2.0*m*q*(q - r) - (b - c)*(r - 1.0));
         q = (q - 1.0)*(r - 1.0)*(s - 1.0);
      end;
      if p > 0, q = -q; else p = -p; end;
      % Is interpolated point acceptable
      if (2.0*p < 3.0*m*q - abs(tol*q)) & (p < abs(0.5*e*q))
         e = d;
         d = p/q;
      else
         d = m;
         e = m;
      end;
   end
   
   % Next point
   c = b;
   fc = fb;
   if abs(d) > tol
      b = b + d;
   else
      b = b - sign(b-a)*tol;
   end
   fb = F(b,varargin{:});
end
