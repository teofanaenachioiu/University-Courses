function x = rezolvare_lup(A, b)
% descompunerea solutiei sistemului pe baza descompunerii LUP
% A - matricea sistemului 
% b - vectorul termenilor liberi
% x - rezultatul sistemului

[L,U,P] = lup(A);
det(L)
U
y = L\(P*b);
x = U\y; 
end