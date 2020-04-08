% 2. Sa se perturbe coeficientii lui f cu numere aleatoare avand 
% distributia N(0, 10^-5) si sa se reprezinte grafic radacinile. 
% Repetati de un numar mare de ori (de exemplu, n = 1000) si 
% pastrati pe ecran punctele reprezentate la fiecare repetare.


% polinomul de evaluat 
p = [1 -2 4/3 -8/27]; 

% radacinile
r = roots(p); 

% desenare radacini
nn = length(r);
h=plot(r,zeros(1,nn),'r.');
set(h,'Markersize',21);
hold on 

% perturbare coeficienti cu numere aleatoare avand distributia normala
% desenare radacini
dim = size(p);
for k=1:1000
    pr = p + normrnd(0,10^-5, dim);
    z = roots(pr);
    h2=plot(z,'b.');
    set(h2,'Markersize',4)
end
axis equal