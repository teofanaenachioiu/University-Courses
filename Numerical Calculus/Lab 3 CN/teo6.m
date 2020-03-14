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