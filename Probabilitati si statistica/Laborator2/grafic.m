function grafic()
    clf
    hold on
    axis([0 10 0 10])
    nrf=0;
    N=10;
    for j=1:N
        x1=0;
        y1=0;
        x2=0;
        y2=0;
        for i=1:6
            u=Benn(0.5);
            if u==0
                y2=y2+1;
            else 
                x2=x2+1;
            end
            line([x1 x2],[y1 y2])
            x1=x2;
            y1=y2;
        end
    if x1==4 && y1==2
        nrf=nrf+1;
        plot(x1,y1,'rx')
    else 
        plot(x1,y1,'gx')
    end
    
    end
    fprintf("Probabilitatea este %4.2f\n",nrf/N)
end