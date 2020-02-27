%basintest
f = @(z) z.^4-1;
fd = @(z) 4*z.^3;
close all
basinNewtonf(f,fd)