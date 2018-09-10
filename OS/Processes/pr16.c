#include<stdlib.h>
#include<unistd.h>
#include<sys/types.h>
#include<sys/wait.h>
int main(){
        int w2s[2],s2c[2];
        pipe(w2s);pipe(s2c);
        if(fork()==0){
                close(w2s[0]);close(s2c[0]);close(s2c[1]);
                dup2(w2s[1],1);
                execlp("who","who",NULL);
        }
        if(fork()==0){
                close(w2s[1]);close(s2c[0]);
                dup2(w2s[0],0);
                dup2(s2c[1],1);
                execlp("sort","sort",NULL);
        }

        if(fork()==0){
                close(w2s[1]);close(w2s[0]);close(s2c[1]);
                dup2(s2c[0],0);
                execlp("cat","cat",NULL);
        }
        close(w2s[1]);close(w2s[0]);close(s2c[1]);close(s2c[0]);
        wait(0);
        wait(0);
        wait(0);
        return 0;

}

