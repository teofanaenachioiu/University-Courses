function x=gammaa(a,n)
    u=rand(1,n);
    x=-sum(log(u))/a;
end