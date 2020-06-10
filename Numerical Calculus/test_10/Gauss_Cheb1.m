function [g_nodes,g_coeff]=Gauss_Cheb1(n)
% Gauss-Chebyshev #1 nodes and coefficients

g_coeff = pi/n*ones(1,n);
g_nodes = cos(pi*([1:n]'-0.5)/n);