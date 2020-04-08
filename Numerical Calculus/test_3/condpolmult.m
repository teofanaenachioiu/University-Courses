function cn = condpolmult(p,xi,m)
%CONDPOLMULT - condition of multiple polynomial roots
% p - polynomial
% xi - roots (default roots p)
% m - multiplicities
% cn - condition numbers (default ones(size(xi)))
% References: Gautschi, Stoer-Bulirsch

if nargin < 2, xi =roots(p); end
if nargin < 3, m = ones(size(xi)); end
if length(xi)~=length(m)
    error('ilegal argument size');
end
cn=zeros(size(xi));
keps=eps.^(1./m-1);
for k=1:length(xi)
    pdm=p;
    for j=1:m(k)    
        pdm=polyder(pdm);
    end
    cn(k)=(factorial(m(k))/abs(xi(k)*polyval(pdm,xi(k)))*...
        polyval(abs(p),xi(k)))^(1/m(k))*keps(k);
end

end

