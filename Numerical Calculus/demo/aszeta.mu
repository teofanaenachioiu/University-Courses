aszeta=proc()
begin
assume(n>0);
series(sum(1/k^2,k=n..infinity),n=infinity,11);
end_proc