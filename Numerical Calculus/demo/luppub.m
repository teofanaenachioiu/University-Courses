%% Descompunere LUP
% -eliminare gaussiana

%% Codul pentru sisteme triunghiulare
% 
%   function [L,U,P]=lup(A)
%   %LUP find LUP decomposition of matrix A
%   %call [L,U,P]=lup(A)
%   %permute effectively lines
% 
%   [m,n]=size(A);
%   P=zeros(m,n);
%   piv=(1:m)';
%   for i=1:m-1
%      %pivoting
%      [pm,kp]=max(abs(A(i:m,i)));
%      kp=kp+i-1;
%      %line interchange
%      if i~=kp
%          A([i,kp],:)=A([kp,i],:);
%          piv([i,kp])=piv([kp,i]);
%      end
%      %Schur complement
%      lin=i+1:m;
%      A(lin,i)=A(lin,i)/A(i,i);
%      A(lin,lin)=A(lin,lin)-A(lin,i)*A(i,lin);
%   end;
%   for i=1:m
%      P(i,piv(i))=1;
%   end;
%   U=triu(A);
%   L=tril(A,-1)+eye(m);
% 
%% Codul pentru sisteme triunghiulare
% 
%   function x=backsubst(U,b)
%   %BACKSUBST - backward substitution
%   %U - upper triangular matrix
%   %b - right hand side vector
%   n=length(b);
%   x=zeros(size(b));
%   for k=n:-1:1
%      x(k)=(b(k)-U(k,k+1:n)*x(k+1:n))/U(k,k);
%   end
%
%   function x=forwardsubst(L,b)
%   %FORWARDSUBST - forward substitution
%   %L - lower triangular matrix
%   %b - right hand side vector
% 
%   x=zeros(size(b));
%   n=length(b);
%   for k=1:n
%      x(k)=(b(k)-L(k,1:k-1)*x(1:k-1))/L(k,k);
%   end
% % 

A = [10,7,0; -3, 2, 6; 5, -1, 5];
b = [7; 4; 6];
%% Exemplu de utilizare
% Descompunere

[L,U,P] = lup(A)
norm(L*U-P*A)
%%
% 
%   function x=lupsolve(A,b)
%   %LUPSOLVE - solution of an algebraic system by LUP decomposition
% 
%   [L,U,P]=lup(A);
%   y=forwardsubst(L,P*b);
%   x=backsubst(U,y);
% 

%% rezolvare efectiva 
%
x = lupsolve(A,b)

%% Exemplul de pe slide-uri
A = [1, 1, 1; 1, 1, 2; 2, 4, 2];
b = [3,4,8]';
[L,U,P] = lup(A)
y = forwardsubst(L,P*b)
x= backsubst(U,y)