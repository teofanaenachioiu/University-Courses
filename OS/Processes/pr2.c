/*
Program simplu
*/

#include<stdlib.h>
#include<unistd.h>
#include<sys/types.h>
#include<sys/wait.h>
#include<stdio.h>
int main(){
        int pid;
        printf("Parinte %d\n",getpid());
        pid=fork();
        if(pid==0){
                printf("Fiu %d %d\n",getpid(),getppid());
                exit(0);
        }
        wait(0);
        printf("Parintele din nou %d\n",getpid());
        return 0;
}
