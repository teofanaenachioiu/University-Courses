#include<stdio.h>
#include<sys/stat.h>
#include<unistd.h>
#include<sys/types.h>
#include<stdlib.h>
#include<fcntl.h>
int main(){
        int a, b,f;
        printf("a=");scanf("%d",&a);
        printf("b=");scanf("%d",&b);
        f=open("/tmp/fifo",O_WRONLY);
        write(f,&a,sizeof(int));
        write(f,&b,sizeof(int));
        close(f);
        return 0;
}
