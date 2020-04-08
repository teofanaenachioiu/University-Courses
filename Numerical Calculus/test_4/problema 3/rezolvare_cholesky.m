function x = rezolvare_cholesky(A, b)
% determinarea solutiei sistemului pe baza descompunerii Cholesky
% A - matricea sistemului 
% b - vectorul termenilor liberi
% x - rezultatul sistemului

R = cholesky(A);
y = forwardsubsttr(R',b);
x = backsubsttr(R,y);
end
