function emax=expomax()
%EXPOMAX maximum exponent FP
%successive multiplication
x=1; emax=-1;
while ~isinf(x)
    x=2*x; emax=emax+1;
end

