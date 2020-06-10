function p = get_cebisev_1_poly(x)
% polinomul cebsev de speta 1
n = length(x);
p = cos(n.*acos(x));