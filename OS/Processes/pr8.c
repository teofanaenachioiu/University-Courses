#include<stdlib.h>
#include<unistd.h>
#include<stdio.h>
#include<sys/types.h>
#include<sys/wait.h>
int main(){
        int rez;
        rez=fork();
        switch(rez){
                case -1: printf("Eroare\n");break;
                case 0: printf("Fiu\n");break;
                default: printf("Parinte\n");wait(0);
        }
        return 0;
}


