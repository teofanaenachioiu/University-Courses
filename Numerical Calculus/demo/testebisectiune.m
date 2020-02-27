%% Metoda bisectiunii (injumatatirii intervalului)
% |BISECTION| calculeaza o radacina a unei ecuatii scalare
% |[x,y]=Bisection(f,a,b,tol)| determina radacina $x$ a functiei scalare
% |f| in intervalul |[a,b]| cu precizia |tol| . |y| este valoarea 
% functiei in $x$
% 
%% Codul pentru injumatatire
%   
%   function [x,y]=Bisection(f,a,b,tol)
%
%   fa=f(a); v=1; 
%   if fa>0, v=-1; end
%   if fa*f(b)>0
%        error('f(a) si f(b) nu au acelasi semn')
%   end
%   if (nargin<4), tol=0; end;
%   x=(a+b)/2;
%   while (b-a>tol) && ((a < x) && (x<b))
%        if v*f(x)>0, b=x; else a=x; end;
%        x=(a+b)/2;
%   end
%   if nargout==2, y=f(x); end;
%   
%   


%% Test bisectiune
%  ecuatia
% 
% $$f(x)=x+\exp(x) = 0$$
% 

[x,y]=Bisection(@(x) x+exp(x),-1,0)

%% Ecuatia lui Kepler
% 
% $$E-e\sin t = \frac{2\pi}{T} t$$
% 

[E,f]=Bisection(@(E) E-0.8*sin(E)-2*pi/10,0,pi,1e-6)