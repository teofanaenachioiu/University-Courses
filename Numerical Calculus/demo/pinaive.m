%% Compute $\pi$, naive version
% Compute area of the unit circle
%%
% 
% <<figpi.png>>
% 

%%
% Area of the triangle is
%
% $$F_{n}=\cos\frac{\alpha_{n}}{2}\sin\frac{\alpha_{n}}{2}$$
% 
% where $\alpha_{n}=\frac{2\pi}{n}$. 
% The total area is
%
% $$A_n=n F_n=\frac{n}{2}\sin \alpha_{n}=\frac{n}{2}\sin\frac{2\pi}{n}.$$
%
% Recurrence relation
%
% $$\sin\frac{\alpha_{n}}{2}=\sqrt{\frac{1-\cos \alpha_{n}}{2}}=\sqrt{\frac{1-\sqrt{1-\sin^2 \alpha_{n}}}{2}}$$
%
%% Initialization
% $n=6$, $\alpha_{6}=\frac{\pi}{3}$, $A_{6}=3\frac{\sqrt{3}}{2}$,
% $\sin\alpha_{6}=\frac{\sqrt{3}}{2}$.

s=sqrt(3)/2; A=3*s; n=6; % initialization
z=[A-pi n A s]; % store the results
while s>1e-10 % termination if s=sin(alpha) small
    s=sqrt((1-sqrt(1-s*s))/2); % new sin(alpha/2) value
    n=2*n; A=n/2*s; % A=new polygon area
    z=[z; A-pi n A s];
end
m=length(z);
disp("      n              A                     err                  sine")
for i=1:m
    fprintf('%10d %20.15f %20.15f %20.15f\n',z(i,2),z(i,3),z(i,1),z(i,4))
end