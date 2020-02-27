format short e
for n=[10:10:100]
    L=abs(eig(hilb(n)));
    c = max(L)/min(L);
    th = (sqrt(2)+1)^(4*n+4)/(2^(15/4)*sqrt(pi*n));
    fprintf('%3d, %11.4e, %11.4e\n', n,c,th)
end