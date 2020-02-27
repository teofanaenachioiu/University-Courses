function [z,ni]=secant(f,x0,x1,ea,er,Nmax)
%SECANT - metoda secantei pentru ecuatii in R
%intrare
%f - functia
%x0,x1 - valori de pornire
%ea,er - eroarea absoluta, resp eroarea relativa
%Nmax - numarul maxim de iteratii
%iesire
%z - aproximatia radacinii
%ni - numar de iteratii

if nargin<6, Nmax=50; end
if nargin<5, er=0; end
if nargin<4, ea=1e-3; end
xv=x0; fv=feval(f,xv);
xc=x1; fc=feval(f,xc);
for k=1:Nmax
    xn=xc-fc*(xc-xv)/(fc-fv);
    if abs(xn-xc)<ea+er*abs(xn) %succes
        z=xn;
        ni=k;
        return
    end
    %pregatesc iteratia urmatoare
    xv=xc; fv=fc;
    xc=xn; fc=feval(f,xn);
end
%esec
error('numarul maxim de iteratii depasit')