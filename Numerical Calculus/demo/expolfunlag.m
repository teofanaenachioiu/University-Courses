clf
x=linspace(-1,1,5);
t=linspace(-1,1,150);
vv=pfl(x,t);
h=plot(x,zeros(size(x)),'r+',t,vv);
set(h(2:6),'Linewidth',2)
set(h(1),'Markersize',12)
legend({'nodes','$\ell_{0}$','$\ell_{1}$','$\ell_{2}$','$\ell_{3}$','$\ell_{3}$'...
    },'Location','bestoutside','Interpreter','LaTex')
grid on