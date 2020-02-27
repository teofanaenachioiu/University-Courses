%% Derivare numerica
% 
%% Deducerea aproximarii 
% Utilizand formula lui Taylor
% 
% $$f(x+h) = f(x) +hf'(x) + \frac{h^2}{2}f''(\xi), \xi \in [x,x+h]$$
% 
% se obtine
%%
% 
% $$f'(x) = \frac{f(x+h)-f(x)}{h}-\frac{h}{2}f''(\xi)$$
% 
% Termenul $-\frac{h}{2}f''(\xi)$ este *eroarea de trunchiere* sau *eroarea de
% discretizare* la aproximarea lui $f'(x)$ prin $\frac{f(x+h)-f(x)}{h}$.
% Eroarea este $O(h)$ si spunem ca precizia este de ordinul I.
% La derivarea numerica vom presupune ca $x+h$ si $x$ se reprezinta exact, 
% iar erorile se comit doar la evaluarea lui $f(x+h)$ si $f(x)$.
% Ignorand erorile de rotunjire la scadere si impartire, se calculeaza
%%
% 
% $$\frac{f(x+h)(1+\delta_{1})-f(x)(1+\delta_2)}{h}=\frac{f(x+h)-f(x)}{h}+ \frac{\delta_1f(x+h)-\delta_2f(x)}{h}$$
% 
% Deoarece $|\delta_1|<eps$ si $|\delta_2|<eps$, eroare de rotunjire este 
% mai mica sau egala cu $2eps|f(x)|/h$, pentru $h$ mic. De notat ca eroarea
% de trunchiere este proportionala cu $h$, iar eroarea de rotunjire este
% proportionala cu $1/h$. Micsorarea lui $h$  micsoreaza eroarea de
% trunchiere, dar creste eroarea de rotunjire.
%% Exemplu
% Luam $f(x)=\sin x$ si $x=\pi/4$. Atunci $f'(x)=\cos x$ si $f''(x)=-\sin
% x$, deci eroarea de trunchiere este de aproximativ $\sqrt{2}h/4$, iar
% eroarea de rotunjire este de aproximativ $\sqrt{2} eps/h$


x = pi/4;
h = 10.^(-(1:16))';
d = (sin(x+h)-sin(x))./h;
[d, sqrt(2)/2*ones(size(d)), abs(d-cos(x))]

%% Precizia maxima
% Precizia maxima se obtine daca cele doua erori sunt aproximativ egale
%
% $$\frac{\sqrt{2}h}{4} = \frac{\sqrt{2}eps}{h} \Rightarrow h=2\sqrt{eps}$$                  
%
% Eroarea este de ordinul $\sqrt{eps}$ 

ho = 2*sqrt(eps); 
do = (sin(x+ho)-sin(x))./ho;
[ho, do]

%% Sursa neplacerii  
% Sursa neplacerii este _algoritmul_ nu problema determinarii 
%%
% 
% $$\frac{d}{dx}\left. \sin{x}\right|_{x=\pi/4} =\left. \cos x \right|_{x=\pi/4}=\frac{\sqrt{2}}{2}$$
% 
% care este bine conditionata
%
%% Conditionarea absoluta
% 
% $$\kappa(x)=\left| -\sin x\right|_{x=\pi/4}=\frac{\sqrt{2}}{2}$$
% 

%% Conditionarea relativa
% 
% $$cond(f)(x)=\left | \frac{x \sin x}{\cos x}\right|_{x=\pi/4}=\frac{\pi}{4}.$$
% 
%% Reprezentare grafica
loglog(h,abs(d-cos(pi/4))/cos(pi/4))
title('Derivare numerica, $f(x)=\sin(x)$, $x_{0}=\frac{\pi}{4}$','Interpreter','LaTeX')



