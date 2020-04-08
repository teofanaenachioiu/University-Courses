function x=eliminare_gausiana(A,b)
% eliminare gaussiana cu pivotare scalata pe coloana
% A - matricea sistemului 
% b - vectorul termenilor liberi
% x - rezultatul sistemului

n = size(A,1); % dimensiunea matricei 
x = zeros(size(b)); % initializare solutiile
s_line = sum(abs(A),2); % suma elementelor in modul pe fiecare linie
A = [A,b]; % constructie matrice extinsa
pivot = 1:n; % initializare pivot (tin evidenta interschimb. de linii)

% parcurgere pana la penultima linie
for i=1:n-1
    % caut valoarea maxima (+ pozitia) unde sa fac pivotarea
    [u,p] = max(abs(A(i:n,i))./s_line(i:n)); 
    p = p+i-1;
    if u==0 
        error('Solutia nu este unica!')
    end
    if p~=i 
        % interschimbare pivotul curent cu linia curenta daca 
        % elementul maxim nu e pe pozitia curenta
        pivot([i,p]) = pivot([p,i]);
    end
    % determinare valori noi pe liniile de sub pivot
    for j=i+1:n
        m = A(pivot(j),i)/A(pivot(i),i); 
        A(pivot(j),i+1:n+1) = A(pivot(j),i+1:n+1)-m*A(pivot(i),i+1:n+1);
    end
end
% determinare solutii
x(n) = A(pivot(n),n+1)/A(pivot(n),n);
for i=n-1:-1:1
    x(i)=(A(pivot(i),n+1)-A(pivot(i),i+1:n)*x(i+1:n))/A(pivot(i),i);
end
end