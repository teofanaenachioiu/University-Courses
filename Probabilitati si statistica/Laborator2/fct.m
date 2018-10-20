function fct()
    clf
    hold on
    axis([0 2 0 2])
    n=1000;
    c=0;
    c1=0;
    for i=1:n
        x=rand()*2;
        y=rand()*2;
        d=sqrt((x-1)*(x-1)+(y-1)*(y-1));
        if d<=1 
            c=c+1;
        end
        if d<=0.5
            c1=c1+1;
        end
        if d>1
            plot(x,y,'bx')
        elseif d<=1 && d >0.5
            plot(x,y,'yx')
        elseif d<=0.5
            plot(x,y,'rx')
        end
    end
    fprintf("P sim: %4.2f\n",c/n);
    fprintf("P teor: %4.2f\n",pi/4);
    fprintf("P sim cerc mic: %4.2f\n",c1/n);
    fprintf("P teor cerc mic: %4.2f\n",pi/16);
end