%CENSUS - example with census data
%          exponetial fit

%data
y = [ 75.995  91.972 105.711 123.203 131.669 150.697 ...
    179.323 203.212 226.505 249.633 281.422 308.786]';

t = (1900:10:2010)';   % census years
x = (1890:1:2019)';    % evaluation years
w = [1975,2015];              % prediction year

%mt=mean(t); st=std(t);

% s=(t-mt)/st;
% xs=(x-mt)/st;
cs=polyfit(t,log(y),1);
K=exp(cs(2)); lambda=cs(1);
fm=@(t) K*exp(lambda*t);
zs=fm(x);
est=fm(w);
plot(t,y,'o',x,zs,'-',w,est,'*')
for i=1:length(w)
    text(w(i),est(i)-20,num2str(est(i)))
end
title('U.S. Population', 'FontSize', 14)
xlabel('year', 'FontSize', 12)
ylabel('Millions', 'FontSize', 12)
