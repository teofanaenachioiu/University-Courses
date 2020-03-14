for n=10:15
        %t=linspace(-1,1,n);
        t = 1./(1:n);
        V = vander(t);
        cond(V, inf)
        %x = [n, norm(V, inf)*norm(inv(V), inf),condest(V), n^n(n+1)]
end