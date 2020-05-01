function [x, nri] = SOR(A,b,omega,x0,nriter,err)
% metoda relaxarii (Successive OverRelaxation)
% A - matricea sistemului
% b - vectorul termenilor liberi
% omega - parametrul relaxarii
% x0 - solutia de pornire
% nriter - numar maxim de iteratii
% err - precizia calculului
% x - solutia
% nri - numar de iteratii necesar convergentei

[m,n]=size(A);

% A=M-N si Ax=b => (M-N)x=b => Mx=Nx+b => x=inv(M)(N*x+b)
% La metoda SOR M=(1/omega)*D-L 
M=1/omega*diag(diag(A))+tril(A,-1);
N=M-A;
x = x0(:);

for i=1:nriter
   x0 = x;
   x = M\(N*x0+b); 
   if norm(x-x0,inf)<err*norm(x,inf) % criteriul: https://amci.unimap.edu.my/images/Artikel/Vol_6_2017/amci_vol_6_2017_41-52.pdf
      nri=i;
      return;
   end
end
error('depasire numar maxim de iteratii')