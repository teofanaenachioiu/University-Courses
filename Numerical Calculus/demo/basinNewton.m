%basinNewton
%basin of attraction for Newton method
n=1000; m=30;
x=-2:2/n:2;
[X,Y]=meshgrid(x,x);
Z=X+1i*Y;
for i=1:m
    Z=Z-(Z.^3-1)./(3*Z.^2);
end
image((round(imag(Z))+2)*10);