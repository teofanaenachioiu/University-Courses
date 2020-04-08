function [L,U,P]=lup_no_swap(A)
% descompunerea LUP a matricei A fara interschimbarea liniilor
% A - matricea de descompus
% L - matricea multiplicarilor
% U - matricea finala
% P - pivotarea
[m,n] = size(A);
P = zeros(m,n);
piv = (1:m)'; % matrice coloana
for j=1:m-1
    i = piv(j);
    % caut valoarea maxima (+ pozitia) unde sa fac pivotarea
    % [maxim,ppoz] = max(abs(A(i:m,i)));
    ppoz = 0;
    maxim = 0;
    for k = j:m
        p = piv(k);
        if abs(A(p,j))>maxim
            maxim = abs(A(p,j));
            ppoz = k;
        end
    end
    if j~=ppoz
        piv([j,ppoz]) = piv([ppoz,j]);
    end
    row = piv(j+1:m);
    col = j+1:m;
    i = piv(j);
    A(row, j) = A(row,j) /A(i,j);
    A(row,col) = A(row,col)-A(row,j) * A(i,col);
end
for i=1:m
    P(i,piv(i)) = 1;
end

U = zeros(size(A));
L = zeros(size(A))+eye(m);

for i=1:m
    for j=i:m
        U(i,j)=A(piv(i),j); 
    end
end

for i=2:m
    for j=1:i-1
        L(i,j)=A(piv(i),j); 
    end
end

