function [x1,x2] = quadform2(a,b,c)
%QUADFORM2 - solve quadratic equation
%call [x1,x2] = quadform(2a,b,c)

d = sqrt(b^2 - 4*a*c);
x1 = (-b - sign(b)*d) / (2*a);
x2 = c/a/x1;