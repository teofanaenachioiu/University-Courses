function x=tridGepp(e,d,f,b)
%TRIDGEPP - Gaussian elimination with partial pivoting for tridiagonal
%system
%call x=triGepp(e,d,f,b)
%Input
%e - first subdiagonal
%d - main diagonal
%f - first superdiagonal
%b - right hand side
%Output
%x - solution

%initialization
f=[f(:);0]; e=[0;e(:)]; d=d(:);
n=length(b)
x=zeros(size(b));
g=zeros(1,n);
%Elimination
for i=1:n-1
    if abs(d(i))<abs(e(i+1)) %pivoting
        [d(i),e(i+1)] = swap(d(i),e(i+1));
        [d(i+1),f(i)] = swap(d(i+1),f(i));
        [g(i),f(i+1)] = swap(g(i),f(i+1));
        [b(i),b(i+1)]=swap(b(i),b(i+1));
    end
    if d(i)==0, error('no unique solution'), end
    
    %j=i+1;
    m=e(i+1)/d(i); %A(j,i)/A(i,i);
    e(i+1)=e(i+1)-m*d(i);%A(j,i+1:n+1)=A(j,i+1:n+1)-m*A(i,i+1:n+1);   
    d(i+1)=d(i+1)-m*f(i);
    f(i+1)=f(i+1)-m*g(i);
    b(i+1)=b(i+1)-m*b(i);
end
%back substitution
x(n)=b(n)/d(n);
x(n-1)=(b(n-1)-f(n-1)*x(n))/d(n-1);
for i=n-2:-1:1
    x(i)=(b(i)-f(i)*x(i+1)-g(i)*x(i+2))/d(i);
end
function [x,y]=swap(x,y)
t=x; x=y; y=t;
