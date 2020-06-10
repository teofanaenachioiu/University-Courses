function [z,ni]=secant(f,x0,x1,ea,er,nmax)
% metoda secantei pentru ecuatii in R
% f - functia
% x0,x1 - valori de pornire
% ea - eroarea absoluta
% er - eroarea relativa
% nmax - numarul maxim de iteratii
% z - aproximatia radacinii
% ni - numar de iteratii

if nargin<6, nmax=50; end
if nargin<5, er=0; end
if nargin<4, ea=1e-3; end

xv=x0; fv=f(xv); xc=x1; fc=f(xc);
for k=1:nmax
    xn=xc-fc.*(xc-xv)./(fc-fv);
    if abs(xn-xc)<ea+er*xn
        z=xn;
        ni=k;
        return
    end
    xv=xc; fv=fc; xc=xn; fc=feval(f,xn);
end
error('numarul maxim de iteratii depasit')