#include<stdlib.h>
#include<unistd.h>
#include<sys/types.h>
#include<sys/wait.h>
#include<stdio.h>
int main(){
        int rez1,rez2,pid,p2c[2],c2p[2];
        rez1=pipe(p2c);
        if(rez1<0){
                printf("eroare la pipe\n");
                exit(0);
        }
        rez2=pipe(c2p);
        if(rez2<0){
                printf("eroare la pipe\n");
                exit(0);
        }
        pid=fork();
        if(pid==-1){
                printf("eroare la fork\n");
                exit(0);
        }
        if(pid==0){
                close(p2c[1]);close(c2p[0]);
                int go=1,n,sum;
                while(go){
                        printf("n: ");scanf("%d",&n);
                        write(c2p[1],&n,sizeof(int));
                        if(n==-1) go=0;
                }
                close(c2p[1]);
                read(p2c[0],&sum,sizeof(int));
                printf("Sum: %d\n",sum);
                exit(0);
        }
        close(p2c[0]);close(c2p[1]);
        int go=1,n,sum=0;
        while(go){
                read(c2p[0],&n,sizeof(int));
                if(n==-1) go=0;
                else sum+=n;
        }
        write(p2c[1],&sum,sizeof(int));
//      wait(0);
        close(p2c[1]);close(c2p[0]);
        return 0;
}

