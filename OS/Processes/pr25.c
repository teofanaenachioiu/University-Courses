#include<stdlib.h>
#include<unistd.h>
#include<stdio.h>
#include<sys/types.h>
#include<sys/wait.h>
int main(){
        int rez1,p2c[2],c2p[2],pid;
        rez1=pipe(p2c);pipe(c2p);
        if(rez1<0){
                printf("Eroare la pipe\n");
                exit(0);
        }
        pid=fork();
        if(pid==-1){
                printf("Eroare la fork\n");
                exit(0);
        }
        if(pid==0){
                close(p2c[1]);close(c2p[0]);
                //fiu
                int go=1,d,copie, curent=2,n;
                //printf("Fiu\n");
                read(p2c[0],&n,sizeof(int));
                while(go){
                        if(curent<n){
                                d=2;
                                copie=curent;
                                while(d<=copie/2){
                                        if(copie%d==0){break;}
                                        d++;
                                }
                                if(d>copie/2) write(c2p[1],&copie,sizeof(int));
                                curent++;
                        }
                        else{copie=0;write(c2p[1],&copie,sizeof(int)); go=0;break;}

                }
                close(p2c[0]);close(c2p[1]);
                exit(0);
        }
        //parinte
        int n;
        close(p2c[0]);close(c2p[1]);
        printf("n=");scanf("%d",&n);
        write(p2c[1],&n,sizeof(int));
        int go=1,curent;
        while(go==1){
                read(c2p[0],&curent,sizeof(int));
                if(curent==0)
                        {go=0;break;}
                else printf("%d\n",curent);
        }
        wait(0);
        close(p2c[1]);close(c2p[0]);
        return 0;
}
