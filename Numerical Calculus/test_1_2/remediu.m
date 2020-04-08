syms y;
color = ['r','b','g','y']
fplot(exp(y),[-1 1],'b');
hold on
for i=1:2:7
    T = taylor((exp(y)-exp(-y))./(2*y), y, 'Order', i)
    fplot(T,[-1 1],'r');
    hold on
end 
hold off

