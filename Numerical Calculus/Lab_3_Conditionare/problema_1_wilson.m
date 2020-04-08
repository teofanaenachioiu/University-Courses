% 1. Fie sistemul (exemplul este datorat lui Wilson) [...] cu 
% solutia [1 1 1 1]T


A = [10,7,8,7; 7,5,6,5; 8,6,10,9; 7,5,9,10];
b = [32; 23; 33; 31];
xe = A\b;
det(A);
inv(A);

% a. perturbam membrul drept
bp = [32.1;22.9;33.1;30.9];
xep = A\bp; % redacinile se schimba total la perturbarea membrului drept
eri1 = norm(bp-b)/norm(b); % eroarea la intrare
ero1 = norm(xep-xe)/norm(xe); % eroarea la iesire
raport1 = ero1/eri1; % reportul
cond(A)

% b. perturbam matricea
Ap = [10,7,8.1,7.2;7.08,5.04,6,5;8,5.98,9.89,9;6.99,4.99,9,9.98];
xep2 = Ap\b; % redacinile se schimba total la perturbarea matricii sist
eri2 = norm(A-Ap)/norm(A); % eroarea la intrare
ero2 = norm(xe-xep2)/norm(xe); % eroarea la iesire
report2 = eri2/ero2;
cond(Ap)