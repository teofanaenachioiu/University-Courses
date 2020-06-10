function px = get_poly_spline(x,c,t)
% determinarea valorilor polinomului de interpolare pentru valorile t
% x - nodurile
% c - coeficientii [c1,c2,c3,c4]
% t - punctele de evaluat 
% px - valorile polinomului in punctele de evaluat

n = length(x);
x = x(:); 
t = t(:);
k = ones(size(t));

for j = 2:n-1
    k(x(j) <= t) = j;
end

s = t - x(k);
px = c(k,4) + s.*(c(k,3) + s.*(c(k,2) + s.*c(k,1)));
