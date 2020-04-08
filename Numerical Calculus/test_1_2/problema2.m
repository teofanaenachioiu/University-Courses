% Problema 2
% 
% Sa se aproximeze derivata lui f(x) = exp(x) in x = 0 cu formula
% f'(x) = (exp(h)-1)/h pentru valori ale lui h de forma h = 10.^(-15:0) 
% si h = 2:01^-k, k = 10:54; reprezentati grafic eroarea, explicati 
% fenomenul si propuneti un remediu.


f =@(x) exp(x); % functia f
syms x;
% derivata lui f(x) in x = 0
f_diff = eval(subs(diff(exp(x)),x,0)); % f'(0) = 1

% aproximarea derivatei
df = @(h)(exp(h)-1)/h;

% aproximarea derivatei pentru un h din intervalul [-10.^-15 0]
figure(1)
fplot(@(x) df(x),[-10.^-15 0],'r');


% Conform definitiei, daca x* este o aproximanta a lui x diferenta
% delta(x) = ||x* - x||/||x|| se numeste EROAREA RELATIVA. Pentru a 
% determina eroare calculam  |f'aprox(x)- f'(0)| / |f'(0)|, cu h in 
% intervalul precizat.


% graficul erorii pentru h = [-10.^-15 0]
figure(2)
fplot(@(x) abs((f_diff - df(x))/f_diff),[-10.^-15 0],'g');

%  aproximarea derivatei pentru un h din intervalul [-2.01.^-10 -2.01.^-54]
figure(3)
fplot(@(x) df(x),[-2.01.^-10 -2.01.^-54],'r');

% eroarea pentru h = [-2.01.^-10 -2.01.^-54]
figure(4)
fplot(@(x) abs((f_diff - df(x))/f_diff),[-2.01.^-10 -2.01.^-54],'g');


% Explicatie:
% Derivata functiei f(x)=exp(x) in punctul x=0 este 1 (e^0=1). Deci, 
% se doreste ca aproximanta noastra sa se apropie cat mai mult de 1 
% pentru valori x apropiate de 0. Cu toate ca limita aproximantei da
% intr-adevar 1 atunci cand x tinde la 0, se observa din graficul 
% functiei ca aceasta nu se comporta asa cum ne-am astepta. 
% Acest lucru apare din cauza erorii metodei si erorii propagate,
% datorita aritmeticii flotante.

% calculul limitei pentru aproximanta.
syms h;
limit(df,h,0)

% Remediere:
% Ca si "remediu" se foloseste forma simetrica (f(x+h)-f(x-h))/(2*h)

remediu = @(x, h) (f(x+h)-f(x-h))/(2*h);
(6*eps)^(1/3) % minimul


% graficul erorii pentru h = [-10.^-15 0]
figure(5)
fplot(@(h) abs((f_diff - remediu(0,h))/f_diff),[-10.^-15 0],'b');

% graficul erorii pentru h = [-2.01.^-10 -2.01.^-54]
figure(6)
fplot(@(h) abs((f_diff - remediu(0,h))/f_diff),[-2.01.^-10 -2.01.^-54],'g');

