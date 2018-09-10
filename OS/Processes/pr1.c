/*
Program simplu
*/

#include<unistd.h>
#include<stdlib.h>
#include<sys/types.h>
#include<sys/wait.h>
#include<stdio.h>
int main(){
        int pid;
        pid=fork();
        if(pid==0){
                printf("Sunt in fiu\n");
                exit(0);
        }
        wait(0);
        printf("Sunt in parinte\n");
        return 0;
        }
