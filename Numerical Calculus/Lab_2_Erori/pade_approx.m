function R = pade_approx(val, f, m, n)
    % interschimbam n cu m daca n>m
    if n>m
        aux=m;
        m=n;
        n=aux;
    end
    % calculul lui c(i)
    syms x
    fact = 1;
    
    coef = zeros(1, m+n+1); 
    
    coef(1) = eval(subs(f,x,0)); % primul termen este f(0).
    for i = 1:m+n
        fact = fact * i;
        fun = diff(f,i)/fact; % c(i) = f derivat de i ori / i!
        coef(i+1)=eval(subs(fun,x,0));
    end
    
    % determinam matricea Toeplitz
    T = toeplitz(coef(m + 1 : m + n), coef(m + 1:-1:m - n + 2));
  
    y = coef( m + n + 1:-1:m + 2); % extragem termienii m+n.. m+1
    y = rot90(y); % transformam in matrice coloana
    y = -1.*y;  % inmultim toata matricea coloana cu -1
   
    b = T \ y; % rezolva sistemul C*b=y (determinam coeficientii b)
    
    b = [1; b]; % adaugam 1 pe prima coloana 
    
    % determinam coeficientii a
    a = zeros(m+1, 1);
    for j=1:m+1
    	for l=1:min(j, n+1) % pentru l>k+1 avem b(l) = 0
    		a(j) = a(j) + coef(j-l+1)*b(l);
    	end
    end
    
    P = polyval(a(m+1:-1:1), val);
    Q = polyval(b(n+1:-1:1), val);
    R = P./Q;