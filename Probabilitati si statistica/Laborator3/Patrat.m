function Patrat()
    clf
    hold on
    axis([0 1 0 1])
    n=500;
    nr=0;
 
    for i=1:n
        x=rand();
        y=rand();
        x1=x;
        y1=y;
        
        if x<0.5
            x=1-x;
        end
        if y<0.5
            y=1-y;
        end
        %plot(x,y,'yx')
        %pause(0.5)
        d1=pdist([x,y; 1, 1]);
        d2=pdist([x,y;0.5,0.5]);
        %fprintf("d1: %d, d2: %d\n",d1,d2)
        if d1>d2
            plot(x1,y1,'rx')
            nr=nr+1;
        else
            plot(x1,y1,'bx')
        end
    end
    fprintf("Probabilitatea geometrica: %4.2f\n",1/2)
    fprintf("Probabilitatea din simulari: %4.2f\n",nr/n)
end