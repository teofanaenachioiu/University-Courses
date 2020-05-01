function px = BaryLagrange(nodes, f, x, weights)
% nodes - nodurile
% f - functia
% x - puncte pentru aproximam functia
% weights - valorile weight-urilor
% px - formula baricentrica

n = length(nodes);

numarator = zeros(size(x));
numitor = zeros(size(x));

for i=1:n
    node = nodes(i);
    tmp = weights(i)./(x-node);
    numarator = numarator+tmp * f(node);
    numitor = numitor+tmp;
end

px = numarator ./ numitor;

end