function s=SimpsonsRule(f,a,b,tol)
% SIMPSONSRULE composite quadrature using Simpson’s rule
% s=SimpsonsRule(f,a,b,tol); computes an approximation of
% int_a^b f(x) dx to a relative tolerance tol using the
% composite Simpson’s rule.

h=(b-a)/2; s1=f(a)+f(b); s2=0;
s4=f(a+h); s=h*(s1+4*s4)/3;
zh=2; sold=2*s;
while abs(sold-s)>tol*abs(s),
    sold=s; zh=2*zh; h=h/2; s2=s2+s4;
    s4=sum(f(a+(1:2:zh)*h));
    s=h*(s1+2*s2+4*s4)/3;
end