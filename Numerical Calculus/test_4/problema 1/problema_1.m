% Sa se analizeze metoda lui Cramer si eliminarea gaussiana 
% din punct de vedere al complexitatii (unitatea de masura 
% va fi numarul de operatii flotante - flops) in cazurile 
% n = 2 si n = 3.

n = 3;
[A,b] = generare_sistem(n);
%A =[1,-1,1;2,-1,1;1,0,1];
%b = [2;2;1];
gauss(A,b)
cramer(A,b)

% Din calcule rezulta ca pentru n=2 metoda cramer are un numar mai mic de flops decat
% eliminarea gausiana (11flops, comparativ cu 17 flops), deci este mai eficienta. 
% In schimb, pentru n=3, eliminarea gausiana are un numar de operatii flotante mai mic, 
% deci in acest caz ea are o complexitate mai mica in comparatie cu metoda cramer. (mai eficienta)