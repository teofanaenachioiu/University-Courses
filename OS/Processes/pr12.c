#include<stdio.h>
#include<stdlib.h>
#include<unistd.h>
#include<sys/types.h>
#include<sys/wait.h>
int main(){
  int rez1,rez2,p[2],p1[2],p2[2],x[20],n=0,curent;
  pipe(p1);pipe(p);pipe(p2);
  rez1=fork();
  if(rez1==0){
    close(p[0]);close(p2[1]);
    do{
      printf("Nr: ");scanf("%d",&curent);
      if (curent!=-1)x[n++]=curent;
      } while(curent!=-1);
    write(p[1],&n,sizeof(int));
    write(p1[1],x,n*sizeof(int));
    read(p2[0],x,n*sizeof(int));

    for(int i=0;i<n;i++){
        printf("%d ",x[i]);
    }
    printf("\n");
    close(p1[0]);close(p1[1]);close(p[1]);close(p2[0]);
    exit(0);
  }
//  wait(0);
  rez2=fork();
  if(rez2==0){
     close(p[1]);
     read(p[0],&n,sizeof(int));
     read(p1[0],x,n*sizeof(int));
    // printf("%d\n",n);
     for(int i=0;i<n;i++){
        if(x[i]%2==0) x[i]=x[i]*2;
       // printf("%d ",x[i]);
     }
     //printf("\n");
     write(p2[1],x,n*sizeof(int));
     close(p1[0]);close(p[1]);close(p[0]);close(p2[0]);close(p2[1]);
     exit(0);
  }

  wait(0);wait(0);
  close(p1[0]);close(p1[1]);close(p[0]);close(p[1]);

  return 0;
