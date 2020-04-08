% Problema 1
%
% La evaluarea pe calculator a functiei f(x)=x^2/(cos(sin(x))^2-1)
% se observa o eroare relativa mare pentru valori x~~0.
% 1. Reprezentati grafic pe intervalul x apartine [-10^-7 10^-7]
%    Explicati ce se intampla.
% 2. Gasiti o metoda de calcul a lui f pentru |x| < 1 la precizia 
%    masinii si scrieti o functie MATLAB pentru calculul lui f. 
%    Reprezentati grafic.


f = @(x) x^2/(cos(sin(x))^2-1); %functia de evaluat

% Cerinta 1:

% reprezentarea grafica a functiei pe intrevalul [-10^-7 10^-7] 
fplot(@(x) f(x),[-10^-2 10^-2],'r');
hold on

% Explicatie:
% La evaluarea functiei date pe un interval de numere foarte mic se 
% observa o denaturare a graficului (acesta nu este asa cum ne-am 
% astepta noi sa fie, adica sa tinda spre -1). Acest lucru este cauza 
% de fenomenul de anulare care are loc in vecinatatea originii.
% La numitor se scad doua valori foarte apropiate.

% calculul limitei
syms x;
limit(f,x,0);


% Cerinta 2:

% Remediere: 
% Pentru a "remedia" valorile in punctele x foarte apropiate de 
% origine se va folosi o varianta modificata a functiei f.
% Mai exact, pentru o vecinatate foarte mica a originii functia 
% se calculeaza folosind Taylor, iar in rest, se va folosi expresia
% initiala:
% f(x) = x^2/(cos(sin(x))^2-1), pentru x > alfa
%        polinom taylor, altfel
% Se considera alfa = 10^-3
% Functia modificata se numeste "f_changed"

fplot(@(x) f_changed(x, 10^-3),[-10^-2 10^-2],'g');
hold off
