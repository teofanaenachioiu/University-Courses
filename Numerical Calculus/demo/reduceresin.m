%% Reducere sin
sin(12532.14)
%% reducere argument sin(x)=sin(x-2k*pi)
k=floor(12532.14/2/pi)
xr=12532.14-2*k*pi
%% comparatie
sin(xr)
abs(sin(xr)-sin(12532.14))/abs(sin(12532.14))
xrs=12532.14-2*k*single(pi)
sin(xrs)
abs(sin(xrs)-sin(12532.14))/abs(sin(12532.14))