function cn = condmultrad_v2(p,r,m)
    % determinarea numarului de conditionare pentru radacinile multiple
    % p - polinomul
    % r - radacinile
    % m - multiplicitatea radacinilor
    % cn - numarul de conditionare
    % Winkler, J. R. (2003). A statistical analysis of the numerical 
    % condition of multiple roots of polynomials (Teorema 3.3.)

    % determinam radacinile daca ele nu apar printre argumentele functiei
    if nargin < 2
        r =roots(p); 
    end
    
    % setam multiplicitatile radacinilor la 1 daca ele nu apar printre 
    % argumentele functiei
    if nargin < 3
        m = ones(size(r)); 
    end
    
    % initializam numerele de conditionare
    cn = zeros(size(r)); 
    
    % repetam pentru fiecare radacina data ca parametru
    for k = 1:length(r) 
        pd = p;
        for j = 1:m(k)    
            % derivare polinom de m ori (m - multiplicitate radacina)
            pd = polyder(pd); 
        end

        eps_init = 1/(eps^(1-1/m(k))*abs(r(k)));
        eval_polinom =  polyval(abs(p),r(k));
        deriv_polinom = polyval(pd,r(k));

        cn(k) = eps_init*((factorial(m(k))*eval_polinom)/(abs(deriv_polinom)))^1/m(k);
    end
end

