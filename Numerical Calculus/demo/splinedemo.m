%% Spline DEMO
% Demonstratie cu spline cubice
%%
% Alegem functia $f(x)=x(x-1)(x-2)(x-3)(x-4)$
% si nodurile $x_k=k$, $k=0,\dots,4$
f = @(x) x.*(x-1).*(x-2).*(x-3).*(x-4);
d=[24,24];
t=linspace(0,4,200)';
x=0:4; y=f(x);

%% Spline complete
[a1,b1,c1,d1]=Splinecubic(x,y,0,d);
s1=valspline(x,a1,b1,c1,d1,t);

%% spline cu derivate secunde
d2=[-100,100];
[a2,b2,c2,d2]=Splinecubic(x,y,1,d2);
s2=valspline(x,a2,b2,c2,d2,t);

%% spline naturale
[a3,b3,c3,d3]=Splinecubic(x,y,2);
s3=valspline(x,a3,b3,c3,d3,t);

%% spline deBoor
[a4,b4,c4,d4]=Splinecubic(x,y,3);
s4=valspline(x,a4,b4,c4,d4,t);

%% reprezentare grafica

yg=f(t);
plot(x,y,'o',t,[yg,s1,s2,s3,s4])
legend('noduri','f','complet','d2','natural','deBoor','Location','best')