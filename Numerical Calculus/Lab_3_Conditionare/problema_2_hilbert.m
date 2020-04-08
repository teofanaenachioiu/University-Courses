% 2. Sa se studieze conditionarea matricei Hilbert Hn in raport
% cu norma euclidiana, n = 10..15.


start = 10;
endd = 15;
cn = zeros(1, end-start);

for k=start:endd
    H = hilb(k);
    cn(k-start+1) = cond(H, 2); % numarul de conditionare
end

plot(start:endd, log10(cn),'r*')