% 3. Generati sisteme cu matrice aleatoare nesingulare ce 
% au solutia [1,...,1]T. Rezolvati-le cu eliminare gaussiana 
% si descompunere LUP.

[A,b] = generare_matrici(10);

eliminare_gausiana(A,b)
rezolvare_lup(A,b)