%% Demonstratie - metoda Gauss-Seidel
%  
%%
% Consideram sistemul 
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
% $$M = tril(A), N = M - A$$
% 

M = tril(A)
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

%% Dupa 20 de iteratii
for k=1:19
    xv = xn;
    xn = M\(N*xv+b);
    ea = norm(xn-xv,inf);
end
xn
ea
er =ea/norm(xn,inf)