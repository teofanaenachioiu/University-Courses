#include<stdio.h>
#include<sys/stat.h>
#include<fcntl.h>
#include<unistd.h>
#include<sys/types.h>
#include<stdlib.h>
int main(){
        int a, b,s=0,f;
        f=open("/tmp/fifo",O_RDONLY);
        read(f,&a,sizeof(int));
        read(f,&b,sizeof(int));
        s=a+b;
        printf("S=%d\n",s);
        close(f);
        return 0;
}
