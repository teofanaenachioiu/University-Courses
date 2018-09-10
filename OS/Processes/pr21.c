#include<string.h>
#include<stdlib.h>
#include<unistd.h>
#include<stdio.h>
#include<sys/types.h>
#include<sys/wait.h>
int main(){
        int rez1,rez2,pid,p2c[2],c2p[2];
        rez1=pipe(p2c);
        if(rez1<0){
                printf("Eroare la pipe\n");
                exit(0);
        }
        rez2=pipe(c2p);
        if(rez2<0){
                printf("Eroare la pipe\n");
                exit(0);
        }
        pid=fork();
        if(pid==-1){
                printf("Eroare la fork\n");
        }
        if(pid==0){
                close(p2c[1]);close(c2p[0]);
                int go=1;
                char str[50];
                while(go==1){
                        read(p2c[0],str,sizeof(str));
                        if(strlen(str)==0) {go=0;break;}
                        printf("Child: %s\n",str);
                        str[strlen(str)-1]='\0';
                        write(c2p[1],str,sizeof(str));
//sleep(1);
                }
                close(p2c[0]);close(c2p[1]);
                exit(0);
        }
        close(p2c[0]);close(c2p[1]);
                int go=1;
                char str[50];
                printf("Sir: ");scanf("%s",str);
                write(p2c[1],str,sizeof(str));
 while(go==1){
                        read(c2p[0],str,sizeof(str));
                        if(strlen(str)==0) {go=0;break;}
                        printf("Parent: %s\n",str);
                       // strcpy(str,str+1);
                        str[strlen(str)-1]='\0';
                        write(p2c[1],str,sizeof(str));
//                      sleep(1);
                }
                close(p2c[1]);close(c2p[0]);
                wait(0);
        return 0;

}
