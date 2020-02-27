function [x1,x2] = quadeqsolve(a,b,c )
%QUADEQSOLVE Fool-proof quadratic equation solver
%   solve x^2+p*x+q=0
delta=b^2-4*a*c;
if ~isinf(delta)
    d=sqrt(delta);
    x1 = (-b - sign(b)*d) / (2*a);
    x2 = c/a/x1;
else
    m=max(abs([a,b,c]));
    w = b+sign(b)*m*sqrt( (b/m)*(b/m)-4*(a/m)*(c/m) );
    x1 = -w/(2*a);
    x2 = -(2*c)/w;
end
end

