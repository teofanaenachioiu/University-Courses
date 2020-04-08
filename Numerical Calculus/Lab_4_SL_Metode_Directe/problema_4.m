% 4. Sa se scrie rutine pentru descompunerea Cholesky a unei 
% matrice hermitiene si pozitiv definite si rezolvarea unui 
% sistem cu o astfel de matrice prin descompunere Cholesky. 
% Testati rutinele pentru matrice generate aleator si sisteme 
% cu matrice aleatoare, dar cu solutie cunoscuta.

A = [1,2,1;2,5,3;1,3,3];
b = [4;10;7];
rezolvare_cholesky(A, b)

[A,b] = generare_matrici(10);
rezolvare_cholesky(A, b)