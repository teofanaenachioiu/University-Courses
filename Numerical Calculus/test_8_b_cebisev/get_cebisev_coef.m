function c = get_cebisev_coef(f,n)
% determina coeficientii cebiserv discreti
% f - functia
% n - gradul
% c - coeficientii

% radacini T_n
x_k = cos((2*[1:n+1]-1)*pi/(2*n+2)); 
y = f(x_k)';

%calculul coeficientilor
c = zeros(1,n+1);

for k = 1:n+1
    c(k) = 2/(n+1) * get_poly_val_cebisev(x_k,k-1) * y;
end