%% Racheta Patriot, Dhahran, Arabia Saudita, 1991
% eroarea pentru 0.1 sec.
format compact
err=0.1-3*sum(2.^(-4*(1:5)-1))
%% 
% eroarea in timp si spatiu
errt=100*3600*10*err
errd=3750*1604/3600*errt
%%
% Daca s-ar fi lucrat in virgula flotanta
errf=eps(single(0.1))
errtf=100*3600*10*errf
errdf=3750*1604/3600*errtf
