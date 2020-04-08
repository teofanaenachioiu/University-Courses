% Iteratia inversa este un algoritm pentru calculul celei mai 
% mici valori proprii (in modul) a unei matrice simetrice A.
% Pentru matrice mari, putem face economie de operatii daca 
% calculam descompunerea LU a matricei A o singura data. 
% Iteratia se realizeaza utilizand factorii L si U. In acest 
% mod, fiecare iteratie necesita doar O(n^2) operatii, in loc 
% de O(n^3) in programul de mai sus. Utilizati functiile 
% dumneavoastra pentru descompunere LUP, substitutie directa 
% si inversa pentru a implementa iteratia inversa. Experimentati
% cu cateva matrice si comparati rezultatele dumneavoastra cu 
% cele furnizate de eig(A).

nr_iteratii = 5;


minim = 5;
maxim = 20-minim;

% Comparatie algoritm cu si fara descompunere LU.
%
% Se genereaza nr_interatii matrici random simetrice de 
% dimensiune n (n este tot un numar generat random). Se 
% determina timpul de executie al algoritmului cu si fara 
% descompunere LU.
%
% PUNCTE ROSII: timp algoritm cu descompunere LU.
% STELUTE ALBASTRE: timp algoritm fara descompunere LU.
for i=1:nr_iteratii
    n = randi(maxim) + minim;
    A = get_sym_matrix(n);
    fct_no_lu = @() aprox_vp(A); % handler
    fct_lu = @() aprox_vp_lu(A); 
    
    t_lu = timeit(fct_lu);
    plot(i,t_lu,'r.','MarkerSize',12);
    hold on
    
    t_no_lu = timeit(fct_no_lu);
    plot(i,t_no_lu,'b*','MarkerSize',5);
    hold on 
end    
hold off

% Din grafic se observa ca descompunerea LU a matricei A 
% imbunatateste timpul de executie al programului.

% Se testeaza corectitudinea algoritmilor (cu si fara 
% descompunere LU)

no_lu_vector = zeros(1,nr_iteratii);
lu_vector = zeros(1,nr_iteratii);
eig_vector = zeros(1,nr_iteratii);

for i=1:nr_iteratii
    n = randi(maxim) + minim;
    A = get_sym_matrix(n);
    no_lu_vector(i) = aprox_vp(A);
    lu_vector(i) = aprox_vp_lu(A);
    eig_vector(i) = min(abs(eig(A)));
end    
hold off

no_lu_vector
lu_vector
eig_vector % cea mai mica valoare proprie in modul