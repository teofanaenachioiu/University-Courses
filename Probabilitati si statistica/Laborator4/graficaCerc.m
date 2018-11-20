function graficaCerc()
    clf
    hold on
    axis equal
    axis off
    n=12;
      
    %initializare
    x=coord();
    for i=1:(n/2)
        rectangle('Position',[x(i), sqrt(1-x(i)*x(i)),0.5,0.5],'Curvature',[1,1],'Facecolor','yellow')
        rectangle('Position',[x(i+6), -sqrt(1-x(i)*x(i)),0.5,0.5],'Curvature',[1,1],'Facecolor','yellow')
        nr=num2str(i-1);
        nr1=num2str(5+i);
        text(x(i)+0.25,sqrt(1-x(i)*x(i))+0.25,nr)  
        text(x(i+6)+0.25,-sqrt(1-x(i)*x(i))+0.25,nr1) 
    end
    poz=0;
    rectangle('Position',[1, 0,0.5,0.5],'Curvature',[1,1],'Facecolor','red')
    nr=num2str(poz);
    text(1.25,0.25,nr) 
    
   
    N=10;
    p=0.5;
    
    for i=1:n
        pause(0.5)
            nr=Bern(p);
            if nr==0
                y=sqrt(1-x(poz+1)*x(poz+1));
                if(poz>6)
                    y=-y;
                end
                rectangle('Position',[x(poz+1), y,0.5,0.5],'Curvature',[1,1],'Facecolor','yellow')
                nr=num2str(poz);
                text(x(poz+1)+0.25,y+0.25,nr) 
                poz=poz-1;
               if poz<0
                   poz=11;
               end
                y=sqrt(1-x(poz+1)*x(poz+1));
                if(poz>6)
                    y=-y;
                end
                if(poz<0)
                    poz=11;
                end
                rectangle('Position',[x(poz+1), y,0.5,0.5],'Curvature',[1,1],'Facecolor','red')
                nr=num2str(poz);
                text(x(poz+1)+0.25,y+0.25,nr) 
            else
                y=sqrt(1-x(poz+1)*x(poz+1));
                if(poz>6)
                    y=-y;
                end
                rectangle('Position',[poz, y,0.5,0.5],'Curvature',[1,1],'Facecolor','yellow')
                nr=num2str(poz);
                text(x(poz+1)+0.25,y+0.25,nr) 
                poz=poz+1;
                
                if(poz>11)
                    poz=0;
                end
                
                y=sqrt(1-x(poz+1)*x(poz+1));
                if(poz>6)
                    y=-y;
                end
                
                rectangle('Position',[x(poz+1), y,0.5,0.5],'Curvature',[1,1],'Facecolor','red')
                nr=num2str(poz);
                text(x(poz+1)+0.25,y+0.25,nr) 
            end
    end
end
    
 
