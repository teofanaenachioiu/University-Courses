function [I,k] = adquad(f,a,b,tol,met)
% aproximare integrala prin cuaduratura adaptativa
% f - functia
% a,b - limitele
% tol - toleranta
% met - cuadratura repetata utilizata

m = 4; % constanta aleasa convenabil

[I1,k1] = met(f,a,b,tol,m);
[I2,k2] = met(f,a,b,tol,2*m);
k = k1 + k2;

if abs(I1 - I2) < tol
    I = I2;
else 
    % divide and conquer
    [Ia,ka] = adquad(f,a,(a+b)/2,tol,met);
    [Ib,kb] = adquad(f,(a+b)/2,b,tol,met);
    I = Ia + Ib;
    k = k + ka + kb;
end