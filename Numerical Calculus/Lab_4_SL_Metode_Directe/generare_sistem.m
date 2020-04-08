function [A,b] = generare_sistem(n)
% genereaza matricele din cerinta 5

partea_inferioara = tril(-1*ones(n),-1);
partea_superioara = zeros(n);
partea_superioara(:,end)= ones(n,1);
partea_superioara = triu(partea_superioara, 1);
A = eye(n,n) + partea_inferioara + partea_superioara;
b = [2:-1:-n+3]';
