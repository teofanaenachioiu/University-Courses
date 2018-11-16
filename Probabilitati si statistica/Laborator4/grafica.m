function grafica()
    clf
    hold on
    axis equal
    axis off
    n=9;
    N=10;
    p=0.5;
    for i=-n:n
        rectangle('Position',[i, 0,0.5,0.5],'Curvature',[1,1],'Facecolor','yellow')
        nr=num2str(i);
        text(i+0.25,0.25,nr)      
    end
    poz=0;
    rectangle('Position',[poz, 0,0.5,0.5],'Curvature',[1,1],'Facecolor','red')
    nr=num2str(poz);
    text(poz+0.25,0.25,nr) 
    pozitii=zeros(1,n+1);
    matrice=zeros(N,n+1);
    
    for j=1:N
        poz=0;
        
        for i=1:n
            nr=Bern(p);
            if nr==0
                
                rectangle('Position',[poz, 0,0.5,0.5],'Curvature',[1,1],'Facecolor','yellow')
                nr=num2str(poz);
                text(poz+0.25,0.25,nr) 
                poz=poz-1;
                pozitii(i+1)=poz;
                rectangle('Position',[poz, 0,0.5,0.5],'Curvature',[1,1],'Facecolor','red')
                nr=num2str(poz);
                text(poz+0.25,0.25,nr) 
            else
               
                rectangle('Position',[poz, 0,0.5,0.5],'Curvature',[1,1],'Facecolor','yellow')
                nr=num2str(poz);
                text(poz+0.25,0.25,nr) 
                poz=poz+1;
                pozitii(i+1)=poz;
                rectangle('Position',[poz, 0,0.5,0.5],'Curvature',[1,1],'Facecolor','red')
                nr=num2str(poz);
                text(poz+0.25,0.25,nr) 
            end
        end
    matrice(j,:)=pozitii;
    end
    
    
    
    pasi=zeros(1,N);
    
    for i=1:N
        nrPasi=0;
        for j=1:n
            if matrice(i,j+1)==nrPasi+1
                nrPasi=nrPasi+1;
            else 
                break
            end
        end
        pasi(i)=nrPasi;
    end
    matrice
    pasi
    media=mean(pasi)
    figure
    hist(matrice)
end