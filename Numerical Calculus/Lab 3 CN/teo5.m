function E = recdirr(n)
%recurenta directa
    E = 1/exp(3);
    for k=2:n
            E= 1-k*E;

    
end

for n = 2: 25
       E = recdirr(n);
       fprintf('E(%2d=%+6.4e\n)', e, E);
end

% pt problema 4
function E = recinvv(n, k)
    E = 0;
    for j=n+k:-1:n+1
            E= (1-E)/j;
    end
    
for k=10:18
    E=recvinvv(1,k);
    err = abs(E-exp(-1))/exp(-1);
    fprintf('k=%2d, err=%e\n)', k,err);
     
end