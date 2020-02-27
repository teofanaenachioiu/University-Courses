%% Demonstratie - metoda relaxarii (SOR)
% Consideram sistemul cu matricea
%%
% 
% $$\left[ \begin{array} [c]{cc}
% 2 & 1\\
% 5 & 7
% \end{array} \right]x = \left[
% \begin{array}
% [c]{c}
% 4\\
% 19
% \end{array}
% \right]
%   $$
% 
%% Initializare

A = [2,1;5,7];
b = [4;19];
xn = [2;1];

%% Pregatirea matricelor metodei
%
% 
% $$M = diag(A)/\omega-L, N = M - A$$
% 
omega = 1; %optim 1.11
D = diag(diag(A)); L=-tril(A,-1);
M = 1/omega*D-L
N = M -A

%% Prima iteratie
xv = xn;
xn = M\(N*xv+b);
ea = norm(xn-xv,inf);
xn, ea

%% A doua iteratie
xv = xn;
xn = M\(N*xv+b);
ea = norm(xn-xv,inf);
xn, ea

%% Dupa 25 de iteratii
for k=1:23
    xv = xn;
    xn = M\(N*xv+b);
    ea = norm(xn-xv,inf);
end
xn
ea
er =ea/norm(xn,inf)