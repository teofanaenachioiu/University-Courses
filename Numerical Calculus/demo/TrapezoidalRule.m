function t=TrapezoidalRule(f,a,b,tol)
%TRAPEZOIDALRULE composite quadrature using the trapezoidal rule
% t=TrapezoidalRule(f,a,b,tol); computes an approximation of
% int_a^b f(x) dx to a relative tolerance tol using the
% composite trapezoidal rule.

h=b-a; s=(f(a)+f(b))/2;
t=h*s; zh=1; told=2*t;
while abs(told-t)>tol*abs(t),
    told=t; zh=2*zh; h=h/2;
    s=s+sum(f(a+(1:2:zh)*h));
    t=h*s;
end;