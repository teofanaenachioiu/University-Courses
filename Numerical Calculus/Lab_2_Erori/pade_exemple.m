% evaluare problema 3

syms x;
f = sin(x);
res_sin = pade_approx(2, f, 4, 5)
g = cos(x);
res_cos = pade_approx(2, g, 4, 5)