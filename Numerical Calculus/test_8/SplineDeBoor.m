function c = SplineDeBoor(x,f)
% x - valorile nodurilor 
% f - valorile functiei in noduri 
% c - coeficientii (c1,c2,c3,c4) 

n = length(x);

if any(diff(x)<0)
    [x,ind]=sort(x); 
else
    ind=1:n;
end 

y = f(ind); 
x = x(:); 
y = y(:);

fdd = [0,0]; % derivatele de ordinul 2 in a, b sunt 0

%auxiliary unknowns - values of the spline derivative
delta_x = diff(x); % diferenta intre valorile adiacente
div_diff = diff(y)./delta_x; % diferente divizate

delta_x_s = delta_x(1:end-1); 
delta_x_d = delta_x(2:end); 

x31 = x(3) - x(1);
xn = x(n) - x(n-2);

% construim matricea coloana rezultat

m1 = ((delta_x(1) + 2*x31)*delta_x(2)*div_diff(1) + delta_x(1)^2*div_diff(2))/x31;
mn = (delta_x(end)^2*div_diff(end-1) + (2*xn+delta_x(end))*delta_x(end-1)*div_diff(end))/xn;

rhs_inside = 3*(delta_x_d.*div_diff(1:end-1) + delta_x_s.*div_diff(2:end)); 
rhs = [m1; rhs_inside; mn];

% construim matricea sistemului
dp_inside = 2*(delta_x_s + delta_x_d);
dp = [delta_x(2); dp_inside; delta_x(end-1)];

dp_above = [0; x31; delta_x_d];
dp_below = [delta_x_s; xn; 0];
lhs = spdiags([dp_below,dp,dp_above], -1:1, n, n);

% determinam necunoscutele "m"
m = lhs\rhs;

% coeficientii
c(:,4) = y(1:end-1); % fi - valoarea functiei
c(:,3) = m(1:end-1); % mi - grad polinoame
c(:,1) = (m(2:end) + m(1:end-1) - 2*div_diff)./(delta_x.^2);
c(:,2) = (div_diff - m(1:end-1))./delta_x - delta_x.*c(:,1);

