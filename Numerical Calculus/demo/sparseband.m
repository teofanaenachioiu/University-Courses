%% Matrice rare si matrice banda
% 
% <latex>
% Matricele rare \c{s}i band\u{a} apar frecvent \^{\i}n calcule tehnice.
% \emph(Raritatea} unei matrice este propor\c{t}ia de elemente zero.
% Func\c{t}ia MATLAB |nnz| num\u{a}r\u{a} elementele nenule din matrice,
% deci raritatea lui |A| este dat\u{a} de
% </latex>
%%
% 
%   density = nnz(A)/prod(size(A))
%   sparsity = 1 -density
%   
%% 
%
% <latex>
% O \emph{matrice rar\u{a}} este o matrice a c\u{a}rei raritate este apropiat\u{a}
% de 1. \emph{L\u{a}\c{t}imea de band\u{a}} a unei matrice este distan\c{t}a
% maxim\{a} de la elementele nenule la diagonala principal\u{a}. 
% </latex>
% 
%%
% 
%   [i,j] = find(A);
%   bandwidth = max(abs(i-j))
% 
%%
% <latex>
% O \emph{matrice band\u{a}} este o matrice a c\u{a}rei l\u{a}\c{t}ime
% de band\u{a} este mic\u{a}. 
% 
% </latex>

