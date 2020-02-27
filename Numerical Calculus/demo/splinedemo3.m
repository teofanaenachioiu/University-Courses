%% Spline DEMO
% Demonstratie cu spline cubice
%%
% Alegem functia $f(x)=\frac{x+1}{x^2+1}$
% si nodurile $x_k=k$, $k=0,\dots,4$
f = @(x) (x+1)./(x.^2+1);
fd = @(x) -(x.^2+2*x-1)./(x.^2+1).^2;
fdd = @(x) (2*(x.^3+3*x.^2-3*x-1))./(x.^2+1).^3;
d=fd([0,4]);
t=linspace(0,4,200)';
x=0:4; y=f(x);

%% Spline complete
[a1,b1,c1,d1]=Splinecubic(x,y,0,d);
s1=valspline(x,a1,b1,c1,d1,t);

%% Spline cu derivate secunde
d2=fdd([0,4]);
[a2,b2,c2,d2]=Splinecubic(x,y,1,d2);
s2=valspline(x,a2,b2,c2,d2,t);

%% Spline naturale
[a3,b3,c3,d3]=Splinecubic(x,y,2);
s3=valspline(x,a3,b3,c3,d3,t);

%% Spline deBoor
[a4,b4,c4,d4]=Splinecubic(x,y,3);
s4=valspline(x,a4,b4,c4,d4,t);

%% Reprezentare grafica

yg=f(t);
plot(x,y,'o',t,[yg,s1,s2,s3,s4])
legend('noduri','f','complet','d2','natural','deBoor','Location','BestOutside')