#include<stdio.h>
#include<unistd.h>
#include<stdlib.h>
#include<sys/types.h>
#include<sys/wait.h>
int main(int argc, char * argv[]){
        int pid,rez,p[2];
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
                int n1,n2;
                close(p[1]);
                read(p[0],&n1,sizeof(int));
                read(p[0],&n2,sizeof(int));
                printf("Restul impa la 3 al sumei: %d\n",(n1+n2)%3);
                close(p[0]);
                exit(0);
        }
        close(p[0]);
        if (argv[1]==NULL || argv[2]==NULL)
                printf("Nu s-au dat doua numere\n");
        else {
                int n1=atoi(argv[1]),n2=atoi(argv[2]);
                //printf("Arg1: %d\n",atoi(argv[1]));
                write(p[1],&n1,sizeof(int));
                write(p[1],&n2,sizeof(int));
        }
        close(p[1]);
        wait(0);
        return 0;
}
