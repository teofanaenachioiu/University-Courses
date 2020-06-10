function [g_nodes,g_coeff]=Gauss_Hermite(n)
% Gauss-Hermite nodes and coefficients

beta=[sqrt(pi),[1:n-1]/2];
alpha=zeros(n,1);
[g_nodes,g_coeff]=Gaussquad(alpha,beta);