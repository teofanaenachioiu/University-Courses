% Sa se studieze conditionarea matricei Vandermonde Vn(t), pentru

% a) tk = -1+k*2/n (puncte echidistante in [-1,1]) 
% si n = 10; 15 in raport cu norma Cebsev.


start = 10;
endd = 15;
cn_a = zeros(1, endd-start);
cn_b = zeros(1, endd-start);

for n = start:endd
    k = linspace(-1,1,n);
    t = -1 + 2 .* (k./n);
    V = vander(t);
    cn_a(n-start+1) = cond(V, inf); % conditionare in functie de norma Cebisev
end

figure(1)
plot(start:endd, log10(cn_a),'r*')

% b) tk = 1/k; k = 1; n si n = 10; 15 in raport cu norma Cebsev.

for n = start:endd
    k = 1:n;
    o = ones(1, n);
    t = o./k;
    v = vander(t);
    cn_b(n-start+1) = cond(v, inf);
end


figure(2)
plot(start:endd, log10(cn_b),'b*')