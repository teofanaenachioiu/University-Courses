#include<stdlib.h>
#include<unistd.h>
#include<sys/types.h>
#include<sys/wait.h>
int main(){
        int rez,p[2];
        pipe(p);
        rez=fork();
        if(rez==0){
                close(p[0]);
                dup2(p[1],1);
                execlp("ls","ls","-l",NULL);
        }
        if(fork()){
                close(p[1]);
                dup2(p[0],0);
                execlp("sort","sort",NULL);
        }
        close(p[0]);close(p[1]);
        wait(0);wait(0);
        return 0;
}



