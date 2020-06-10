syms t
ved = double(vpa(int(1/sqrt(sin(t)),t,sym(0),sym(pi)/sym(2))))

% aplicam schimbarea de variabila t=asin((u+1)/2)
% capetele intervalului devin -1,1
f = @(x) sqrt(2)./sqrt(x+3);
a=-1/2;
b=-1/2;
err = 1e-9;
n=10;

[t,w] = Gauss_Jacobi(n,a,b);
I0 = vquad(t,w,f)

while 1
    n = n+1;
    [t,w] = Gauss_Jacobi(n,a,b);
    I = vquad(t,w,f);
    if abs(I0-I)<err || n>1000
        I = I0
        break;
    end
    I0 = I;
end

