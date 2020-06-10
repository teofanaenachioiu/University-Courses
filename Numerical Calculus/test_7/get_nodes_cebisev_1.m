function x = get_nodes_cebisev_1(m,a,b)
% determina nodurile cebsev de speta a doua
% m - numar noduri
% a, b - capetele intervalului
% x - nodurile
    x = cos((2.*[0:m]+1)*pi/(2*m+2))*1/2*(b-a)+1/2*(a+b);
end