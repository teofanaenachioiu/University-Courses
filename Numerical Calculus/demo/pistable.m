%% Computation of $\pi$, stable version
% 
%%
% We rewrite the recurrence relation as
%
% $$\sin\frac{\alpha_n}{2}=\frac{\sin\alpha_{n}}{\sqrt{2\left(1+\sqrt{1-\sin^{2}\alpha_{n}}\right)}}$$
% 

oldA=0;s=sqrt(3)/2; newA=3*s; n=6; % initialization
z=[newA-pi n newA s]; % store the results
%% 
% The stopping criterion
%
% $$A_{6}<\dots<A_{n}<\pi$$
%
% However, this cannot be true forever in finite precision arithmetic, since there
% is only a finite set of machine numbers. Thus, the situation $A_n \geq A_{2n}$ must
% occur at some stage, and this is the condition to stop the iteration. Note that
% this condition is independent of the machine, in the sense that the iteration
% will always terminate as long as we have finite precision arithmetic, and when
% it does terminate, it always gives the best possible approximation for the
% precision of the machine.

while newA>oldA % quit if area does not increase
    oldA=newA;
    s=s/sqrt(2*(1+sqrt((1+s)*(1-s)))); % new sine value
    n=2*n; newA=n/2*s;
    z=[z; newA-pi n newA s];
end
m=length(z);
disp("      n              A                     err ")
for i=1:m
    fprintf('%10d %20.15f %20.15f\n',z(i,2),z(i,3),z(i,1))
end