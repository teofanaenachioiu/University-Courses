function vi=Gauss_Legendre_ab(f,n,a,b,tol)

[t,w] = Gauss_Legendre(n);
vi = (b-a)/2*w*f((b-a)/2*t+(b+a)/2);

while 1
   n = n+1;
   [t,w] = Gauss_Legendre(n);
   I = (b-a)/2*w*f((b-a)/2*t+(b+a)/2);
   if abs(max(vi) - max(I))<=tol
       vi = I;
       return;
   end
   vi = I;
end
