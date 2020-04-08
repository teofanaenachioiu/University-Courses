function lambda = aprox_vp_lu(A)
[n,n] = size(A);
x = rand(n,1);
[L,U,P] = lup(A);
true = 1;
while true==1
    y = forwardsubsttr(L,P*x);
    x_next = backsubsttr(U,y);
    x_next=x_next/norm(x_next);
    if norm(x-x_next)<=10^-4
       true = 0;
    end
    x=x_next;
end
lambda=x'*A*x;