function c = SplineComplete(x,f,fd)
% x - valorile nodurilor 
% f - valorile functiei in noduri 
% fd - valorile derivatei in punctele a si b [f'(a),f'(b)]
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

%auxiliary unknowns - values of the spline derivative
delta_x = diff(x); % diferenta intre valorile adiacente
div_diff = diff(y)./delta_x; % diferente divizate

delta_x_s = delta_x(1:end-1); 
delta_x_d = delta_x(2:end); 

% m1 = f'(a), mn = f'(b)
m1 = fd(1); % derivata in capatul a al intrevalului
mn = fd(2); % derivata in capatul b al intrevalului
   
% construim matricea coloana rezultat
rhs_inside = 3*(delta_x_d.*div_diff(1:end-1) + delta_x_s.*div_diff(2:end)); 
rhs = [m1; rhs_inside; mn];

% construim matricea sistemului
dp_inside = 2*(delta_x_s + delta_x_d);
dp = [1; dp_inside; 1];
dp_above = [0; 0; delta_x_d];
dp_below = [delta_x_s; 0; 0];
lhs = spdiags([dp_below,dp,dp_above], -1:1, n, n);

% determinam necunoscutele "m"
m = lhs\rhs;

% coeficientii
c(:,4) = y(1:end-1); % fi - valoarea functiei
c(:,3) = m(1:end-1); % mi - grad polinoame
c(:,1) = (m(2:end) + m(1:end-1) - 2*div_diff)./(delta_x.^2);
c(:,2) = (div_diff - m(1:end-1))./delta_x - delta_x.*c(:,1);

