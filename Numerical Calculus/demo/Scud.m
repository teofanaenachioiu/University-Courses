%Dhahran Scud - Patriot
format long
x=2^(-4)+2^(-5)+2^(-8)+2^(-9)+2^(-12)+2^(-13)+2^(-16)+2^(-17)+2^(-20)+2^(-21);
%sau x=bin2dec('11001100110011001100')/2^23;
TimeError=abs(360000-3600000*x);
DistanceError=3750*1604/3600*TimeError
