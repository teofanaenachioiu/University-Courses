#include<stdio.h>
#include<stdlib.h>
#include<unistd.h>
#include<sys/types.h>
#include<sys/wait.h>
#include<fcntl.h>
int main(){
        int a[]={1,2,3,4},f,g;
//      f=open("/tmp/fifo",O_RDONLY);
        if(fork()==0){
                g=open("fifo",O_WRONLY);
                a[2]+=a[3];
                write(g,&a[2],sizeof(int));//printf("%d\n",a[2]);
                close(g);
                exit(0);
        }
        a[0]+=a[1];
        f=open("fifo",O_RDONLY);
        read(f,&a[2],sizeof(int));
        close(f);
        wait(0);
        a[0]+=a[2];
        printf("Suma: %d\n",a[0]);
        return 0;
}
