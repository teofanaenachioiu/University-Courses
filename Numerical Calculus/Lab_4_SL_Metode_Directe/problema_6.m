% 6. Scrieti rutine pentru descompunerea LUP in care permutarea 
% sa se faca fizic si logic (cu vectori de permutari) si comparati
% timpii de executie al ambelor variante pentru sisteme cu 
% dimensiunea intre 100 si 300.


for n=100:300
    [A,b] = generare_matrici(n);
    l1 = @() lup(A); % handle to function
    l2 = @() lup_no_swap(A); % handle to function
    
    t1 = timeit(l1);
    plot(n,t1,'r.','MarkerSize',4);
    hold on
    
    t2 = timeit(l2);
    plot(n,t2,'k.','MarkerSize',4);
    hold on
end 
hold off

% Din grafic se observa ca descompunerea LUP cu intreschimbarea
% liniilor matricei duce la un timp mai mare de executie comparativ
% cu descompunerea ce nu implica interschimabarea.

