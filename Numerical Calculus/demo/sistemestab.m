%% Test stabilitate EG si QR
% exemplu de matrice bine conditionata pentru care EG
% este instabila
%%

%% Generare A si b
% A are o forma speciala
% 
%%
% 
% <latex>
% $$a_{ij}=\left\lbrace
% \begin{array}{rl}
%     1, & \hbox{pentru $i=j$ sau $j=n$;} \\
%     -1, & \hbox{pentru $i>j$;} \\
%     0, & \hbox{\^{\i}n rest.} \\
% \end{array}%
% \right.$$
% </latex>
% 

n=100;
A=[-tril(ones(n,n-1),-1)+eye(n,n-1),ones(n,1)];
b=A*ones(n,1);

%% Rezolvare cu LUP
% utilizeaza \

x=A\b;
reshape(x,10,10)
norm(b-A*x)/norm(b)
cond(A)

%% Rezolvare cu QR
% utilizeaza qr si \


[Q,R]=qr(A);
x2=R\(Q'*b);
reshape(x2,10,10)