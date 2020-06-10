function [z,ni] = Newton(f,fd,x0,ea,er,nmax)
% metoda lui Newton pentru ecuatii neliniare in R si R^n
% f - functia
% fd - derivata
% x0 - aproximatia initiala 
% ea - eroarea absoluta
% er - eroarea relativa
% nmax - numarul maxim de iteratii
% z - aproximatia radacinii
% ni - numarul de iteratii

if nargin < 6, nmax=50; end
if nargin < 5, er=0; end
if nargin < 4, ea=1e-3; end

xp=x0(:);  
for k=1:nmax
    xc=xp-fd(xp)\f(xp)
    if norm(xc-xp,inf)<eps
        z=xc; 
        ni=k;
        return
    end
    xp=xc;
end

error('S-a depasit numarul maxim de iteratii');
