function [x, nri] = Gauss_Seidel(A,b,x0,nriter,err)
% metoda lui Gauss Seidel
% A - matricea sistemului
% b - vectorul termenilor liberi
% x0 - solutia de pornire
% nriter - numar maxim de iteratii
% err - precizia calculului
% x - solutia
% nri - numar de iteratii necesar convergentei

[m,n]=size(A);

% A=M-N si Ax=b => (M-N)x=b => Mx=Nx+b => x=inv(M)(N*x+b)
% La metoda Gauss-Seidel M=D-L
M=diag(diag(A))+tril(A,-1);
N=M-A;
x=x0(:);

for i=1:nriter
   x0 = x;
   x = M\(N*x0+b); 
   if norm(x-x0,inf)<err*norm(x,inf) % criteriul: https://amci.unimap.edu.my/images/Artikel/Vol_6_2017/amci_vol_6_2017_41-52.pdf
      nri=i;
      return;
   end
end
error('depasire numar maxim de iteratii')