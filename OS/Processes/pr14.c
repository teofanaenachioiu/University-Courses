#include<stdlib.h>
#include<stdio.h>
#include<unistd.h>
#include<sys/types.h>
#include<sys/wait.h>
#include<fcntl.h>
int main(){
        int n,f2g,g2f,ok=0,curent=2,nr=0;
        f2g=open("fisf2g",O_WRONLY);
        g2f=open("fisg2f",O_RDONLY);
        printf("n=");scanf("%d",&n);
        write(f2g,&n,sizeof(int));
        while(nr<n){
                printf("Nr:%d, n:%d\n",nr,n);
                write(f2g,&curent,sizeof(int));
                read(g2f,&ok,sizeof(int));
                if(ok==1){
                        printf("Nr prim: %d\n",curent);
                        nr++;
                }
                curent++;
        }
        close(f2g);close(g2f);
        return 0;
}

