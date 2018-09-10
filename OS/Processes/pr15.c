
#include<stdlib.h>
#include<stdio.h>
#include<unistd.h>
#include<sys/types.h>
#include<sys/wait.h>
#include<string.h>
int main(int argc, char ** argv){
        int i;
        char nume[100];
        for(i=1;argv[i];i++){
                if(fork()==0){
                        strcpy(nume,argv[i]);
                        strcat(nume,".cap");
                        execl("./cap","./cap",argv[i],nume,NULL);
                }
        }
        for(i=1;argv[i];i++){
                wait(0);
        }
        return 0;
}

