function [A,b] = get_sistem_matrice_diagonal_dominanta(n)
max_element = 50;
A = randi([-max_element,max_element],n);
out = sum(abs(A),2) + randi(max_element, n,1);
for i=1:n
    A(i,i) = out(i);
end
b = A\([1:n]');