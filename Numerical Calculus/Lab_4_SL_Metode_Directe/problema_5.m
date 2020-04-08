% Rezolvati sistemul ..... prin descompunere LUP si QR. 
% Ce se observa? Explicati.

[A,b] = generare_sistem(110);
x1 = rezolvare_lup(A,b);
x2 = rezolvare_QR(A,b);
% Pentru valori mari ale lui n (n>=54), solutiile nu mai sunt egale. 
% Problema apare la descompunerea LUP. Pentru n suficient de mare,
% matricea U devine rara (aproape toate elementele sunt 0) si astfel
% apar erori la determinarea solutiei finale.