%% FLOATING POINT DEMO
% Shows several MATLAB floating point facilities such as:
% overflow,underflow, cancellation, machine epsilon, special quantities
% IEEE standard

%% Floating Point Demo
% 

format long
format compact

%% Overflow/underflow
1
-1
%%
1e100
1e-100
%%
1e400
1e-400

%% Cancellation

x=rand
y=rand
z=x-y
%%
x1=x+1e10
y1=y+1e10
z1=x1-y1

z1-z

%% Epsilon

1+1e-20
(1+1e-20)-1
%%
1+1e-16
1+2e-16
(1+2e-16)-1
%%
e=1;
while (1+e>1) e=e/2, end % Don't optimize!
eps
%%
b=2^50
(b+e*b)-b
%% realmin and realmax
realmin
realmax



%% Signed zeros

0
+0
-0

%% Infinity

1/0
-1/0
0/0

%%
inf
1/inf
-1/inf
-1/-inf

2*inf
inf+inf
inf^inf

%% NaN

inf-inf
inf/inf
0/0
nan+123

%% Check for NaN

x=nan;
isnan(x)
x==x

isnan([1,2,3,nan,inf])
isinf([1,2,3,nan,inf])

%% Round to even

e=eps/2
1+e
1+2*e
((1+2*e)-1)/e
((1+3*e)-1)/e
((1+4*e)-1)/e
[0:16; ((1+(0:16)*e)-1)/e]'

%% View hex/bin representations

format hex

0
-0
inf
-inf
nan
-nan
123123+nan
1
2
(1:10)'
realmin
realmax
eps

%% Representation - short
xs=[0,-0,inf,-inf,nan,-nan,1:10,1+(0:10)*2^-23,2-(10:-1:0)*2^-23];
format short
for x=xs
  fprintf('%10.8g %s\n',x,num2bin(single(x),true));
  %pause
end

%% Representation - long
xs=[0,-0,inf,-inf,nan,-nan,1:10,1+(0:10)*2^-23,2-(10:-1:0)*2^-23];
format long
for x=xs
  fprintf('%10.8g %s\n',x,num2bin(x,true));
  %pause
end

%% Contradiction?
format long
realmax
log2(ans)
2^1024

%% Explanation
% This looks like a contradiction at first glance, since the largest exponent
% should be $2^{2046-1023} = 2^{1023}$ according the IEEE conventions. But
% |realmax| is the number with the largest possible exponent and with the
% semnificant F consisting of all ones:
format hex
realmax
%%
% Even though Matlab reports |log2(realmax)=1024| , |realmax| does not
% equal $2^{1024}$, but rather $2^{1023}(2-eps)$; taking the logarithm of |realmax|
% yields 1024 only because of rounding. Similar rounding effects would
% also occur for machine numbers that are a bit smaller than |realmax| .


