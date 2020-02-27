f=@(t,y) -y+t+1;
yex=@(t) t+exp(-t);
N=10;
alfa=1;
tspan=[0,1];
[t,w]=RK4(f,tspan,alfa,N);
we=yex(t);
M=[t,w,we,abs(w-we)];
fprintf('%3.1f, %13.11f, %13.11f, %7.5e\n',M');