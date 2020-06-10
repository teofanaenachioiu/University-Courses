function [I,k] = quadstep(f,a,b,tol,fa,fc,fb)
% calculeaza recursiv valoarea aproximativa a integralei
% f - functia
% a,b - limitele
% tol - toleranta
% fa,fb,fc - valorile f(a),f(b),f(c)

h = b - a;
c = (a + b)/2;
fd = f((a+c)/2);
fe = f((c+b)/2);

Q1 = h/6 * (fa + 4*fc + fb);
Q2 = h/12 * (fa + 4*fd + 2*fc + 4*fe + fb);

if abs(Q2 - Q1) <= tol
    I = Q2 + (Q2 - Q1)/15;
    k = 2;
else
    [Ia,ka] = quadstep(f, a, c, tol, fa, fd, fc);
    [Ib,kb] = quadstep(f, c, b, tol, fc, fe, fb);
    I = Ia + Ib;
    k = ka + kb + 2;
end