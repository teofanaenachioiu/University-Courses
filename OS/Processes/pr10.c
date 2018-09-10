#include<stdio.h>
#include<stdlib.h>
#include<unistd.h>
#include<sys/types.h>
#include<sys/wait.h>
int main(){
        int a=0,b=0,s=0,p2c[2],c2p[2];
        pipe(p2c);pipe(c2p);
        if (fork()){
                close(p2c[1]);close(c2p[0]);
                read(p2c[0],&a,sizeof(int));
                read(p2c[0],&b,sizeof(int));
                s=a+b;
                write(c2p[1],&s,sizeof(int));
                close(p2c[0]);close(c2p[1]);
                exit(0);
        }
        printf("a=");scanf("%d",&a);
        printf("b=");scanf("%d",&b);
        write(p2c[1],&a,sizeof(int));
        write(p2c[1],&b,sizeof(int));
        wait(0);
        read(c2p[0],&s,sizeof(int));
        printf("S=%d\n",s);
        close(p2c[1]);close(p2c[0]);close(c2p[0]);close(c2p[1]);
        return 0;
}



