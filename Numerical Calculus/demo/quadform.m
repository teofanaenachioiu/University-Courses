function [x1,x2] = quadform(a,b,c)
%QUADFORM - solve quadratic equation
%call [x1,x2] = quadform(a,b,c)

d = sqrt(b^2 - 4*a*c);
x1 = (-b + d) / (2*a);
x2 = (-b - d) / (2*a);