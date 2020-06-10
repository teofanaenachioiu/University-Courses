syms t;
n = 5;
tol = 1e-8;

while 1
   R = vpa(1/factorial(2*n)*int(exp(-t^2)*hermiteH(n,t)^2,t,sym(-Inf),sym(Inf))); 
    if R<tol
        R, n, break;
    end
    n = n+1;
end

f1 = @(x) sin(x);
f2 = @(x) cos(x);
[g_nodes,g_coef] = Gauss_Hermite(n);

v1 = vquad(g_nodes,g_coef, f1)
v2 = vquad(g_nodes,g_coef, f2)

v1m = double(int(exp(-t^2)*sin(t),t,sym(-Inf),sym(Inf)))
v2m = double(int(exp(-t^2)*cos(t),t,sym(-Inf),sym(Inf)))