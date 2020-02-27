%% Conditionarea matricei lui Hilbert
% Calculeaza conditionarea matricei Hilbert $cond_{2}(H_{n})$
% cu ajutorul valorilor proprii.
% Se va utiliza formula
%%
%
% $$\mathrm{cond}_{2}(H_n) = \lambda_{\max}(H_n)\lambda_{\max}(H_{n}^{-1})$$
% 
% O estimare teoretica, pentru $n$ mare, datorata lui Szego, este
%%
% 
% $$cond_{2}(H_n) = \frac{(1+\sqrt{2})^{4n+4}}{2^{15/4}\sqrt{\pi n}}$$
% 

format short e
for n=[10:10:100]
    c = max(eig(hilb(n)))*max(eig(invhilb(n)));
    th = (sqrt(2)+1)^(4*n+4)/(2^(15/4)*sqrt(pi*n));
    fprintf('%3d, %11.4e, %11.4e\n', n,c,th)
end