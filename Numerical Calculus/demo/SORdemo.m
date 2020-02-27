%% Demonstratie - metoda SOR
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
omega =  1.11001; %SOR
%omega =1; %Gauss-Seidel

%% Pregatirea matricelor metodei
%
% $$A = D-L-U$$
%
% $$M = \frac{1}{\omega}D - L, N = M - A$$
% 

M=1/omega*diag(diag(A))+tril(A,-1)
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