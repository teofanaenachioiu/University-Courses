function y = get_aprox_cebisev(c,x)
% determina aproximanta cu mcmmp discreta si polinoame cebisev de speta 1
% c - coeficientii
% x - punctele
% y - valoarea aproximantei

% determin aproximanta
y = c(1)/2 * ones(size(x));
for k = 1:length(c)-1
    y = y + c(k+1)*get_poly_val_cebisev(x,k);
end
