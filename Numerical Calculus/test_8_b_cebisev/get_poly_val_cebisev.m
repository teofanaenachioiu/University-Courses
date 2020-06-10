function y = get_poly_val_cebisev(x,n)
% determina valorilor polinomului Cebisev pe baza relatiei de recurenta
% x - punctele in care se evalueaza
% n - gradul polinomului
% y - valorile polinomului Cebisev

T_k_1 = ones(size(x));
T_k = x;

if n==0
    y = T_k_1;
    return; 
end

if n==1 
    y = T_k; 
    return; 
end

% recurenta
for k = 2:n
    y = 2*x.*T_k - T_k_1;
    T_k_1 = T_k;
    T_k = y;
end