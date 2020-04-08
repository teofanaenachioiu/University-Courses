n = 1000;
p = [1 -2 4/3 -8/27];
dim = size(p);

r = roots(p);
sols = polyval(p, r);

for j = 1:dim
    plot(r(j), sols(j), 'r*')
    hold on
end

for i=1:n
    pp = p + normrnd(0,10^-5,dim);
    rp = roots(pp);
    sol = polyval(pp, rp);
    for j = 1:dim
        plot(rp(j), sol(j), 'c*')
        hold on
    end
end
hold off

