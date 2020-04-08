function cn = condmultrad(p,r,m)
%CONDPOLMULT - condition of multiple polynomial roots
% p - polynomial
% xi - roots (default roots p)
% m - multiplicities
% cn - condition numbers (default ones(size(xi)))
% References: Gautschi, Stoer-Bulirsch

    keps = eps^(1/m-1);
   
    pdm=p;
    for j=1:m    
        pdm=polyder(pdm);
    end
    cn=(factorial(m)/abs(r*polyval(pdm,r))* polyval(abs(p),r))^(1/m)*keps;

end

