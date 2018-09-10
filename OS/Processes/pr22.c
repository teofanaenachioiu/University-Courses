#include<stdio.h>
#include<stdlib.h>
#include<unistd.h>
#include<sys/types.h>
#include<sys/wait.h>
int main(){
        int rez,pid,p[2];
        rez=pipe(p);
        if(rez<0){
                printf("Eroare la pipe\n");
                exit(0);
        }
        pid=fork();
        if(pid==-1){
                printf("Eroare la fork\n");
                exit(0);
        }
        if(pid==0){
                close(p[1]);
                int go=1,curent,sum=0;
                while(go){
                        read(p[0],&curent,sizeof(int));
                        if(curent==0) {go=0;break;}
                        sum+=curent;
                }
                printf("Sum: %d\n",sum);
                close(p[0]);
                exit(0);
        }
        close(p[0]);
        int go=1,curent;
        while(go){
                printf("NR: ");scanf("%d",&curent);
                write(p[1],&curent,sizeof(int));
                if (curent==0){go=0;break;}
        }
        close(p[1]);
        wait(0);
        return 0;

}
