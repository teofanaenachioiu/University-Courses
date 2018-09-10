#include<stdlib.h>
#include<unistd.h>
#include<stdio.h>
#include<sys/types.h>
#include<sys/wait.h>
int main(){
        int rez, s=-1,c2p[2], a[]={1,2,3};
        pipe(c2p);
        rez=fork();
        if(rez){
                close(c2p[0]);
                s=a[0]+a[1]+a[2];
                write(c2p[1],&s,sizeof(int));
                close(c2p[1]);
                //printf("s=%d\n",s);
                exit(0);
        }
        wait(0);
        read(c2p[0],&s,sizeof(int));
        printf("Suma: %d\n",s);
        close(c2p[0]);close(c2p[1]);
        return 0;
}


