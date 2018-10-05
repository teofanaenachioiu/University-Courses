function lab01()
  clf
  hold on
  x=[0 :0.01: 3];
  y=x.^2+3*x-1;
  plot(x,y,'dm')
  z=sin(x);
  plot(x,z,'og')
  title('First lab')
  axis([-2 5 -2 5])
  line()
end