function x = rezolvare_cholesky(A, b)
% determinarea solutiei sistemului pe baza descompunerii Cholesky
% A - matricea sistemului 
% b - vectorul termenilor liberi
% x - rezultatul sistemului

R = cholesky(A);
y = (R.')\b;
x = R\y; 
end
