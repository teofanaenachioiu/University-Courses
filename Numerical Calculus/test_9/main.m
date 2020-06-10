% Determinati lungimea arcului de curba parametrica
% x=@(t) (1-cos(t)).*cos(t) si y=@(t) (1-cos(t)).*sin(t)
% t in [0,2*pi], folosind  o cuadratura adaptiva si metoda 
% lui Romberg.

syms t;

a = 0;
b = 2*pi;

x = @(t) (1-cos(t)).*cos(t);
dx = @(t) cos(t).*sin(t) + sin(t).*(cos(t) - 1); % derivata lui x(t)
y = @(t) (1-cos(t)).*sin(t);
dy = @(t) sin(t).^2 - cos(t).*(cos(t) - 1); % derivata lui y(t)

flung = @(t) sqrt(dx(t).^2+dy(t).^2); % lungimea arcului
prec = 1e-3; % precizia aproximarii

tic
% Metoda Romberg
[IR, cntR] = Romberg(flung,a,b, prec, 1000)
toc

tic
% Cuaduratura adaptive 1 (+ Romberg ca metoda)
[IA1,cntA1] = adquad(flung,a,b,prec, @Romberg)
toc

tic
% Cuaduratura adaptive 2
[IA2,cntA2] = adquad2(flung,a,b,prec)
toc

% afisare curba parametrica
nodes = linspace(a,b,100);
plot(x(nodes),y(nodes),'-');
title('Curba parametrica');