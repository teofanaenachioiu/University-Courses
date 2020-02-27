function A=AitkenExtrapolation(T,h0,tol,factor)
% AITKENEXTRAPOLATION Aitken-Neville scheme for extrapolation
% A=AitkenExtrapolation(T,h0,tol,factor); computes the
% Aitken-Neville Scheme for the function T and h_i=h0/2^i until the
% relative error of two diagonal elements is smaller than tol. The
% parameter factor is 2 or 4, depending on the asymptotic expansion
% of the function T.

h=h0; A(1,1)=T(h);
for i=2:15
    h=h/2; A(i,1)=T(h); vhj=1;
    for j=2:i
        vhj=vhj/factor;
        A(i,j)=(vhj*A(i-1,j-1)-A(i,j-1))/(vhj-1);
    end;
    if abs(A(i,i)-A(i-1,i-1))<tol*abs(A(i,i)), return
    end
end
warning(['limit of extrapolation steps reached. ', ...
    'Required tolerance may not be met.']);