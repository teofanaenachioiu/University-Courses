#include<stdlib.h>
#include<unistd.h>
#include<stdio.h>
#include<sys/types.h>
#include<sys/wait.h>
int main(){
  int a2b[2],b2c[2],c2a[2],n;
  pipe(a2b);pipe(b2c);pipe(c2a);
  if(fork()){
    //Sunt in A
    close(a2b[0]);close(b2c[0]);close(b2c[0]);close(b2c[1]);
    n=5;
    write(a2b[1],&n,sizeof(int));
    while(n>=0){
      read(c2a[0],&n,sizeof(int));
      printf("A: %d\n",n);
      n--;
      write(a2b[1],&n,sizeof(int));
    }
    close(a2b[1]);close(c2a[0]);
    exit(0);
  }

  if(fork()){
    //Sunt in B
    close(a2b[1]);close(b2c[0]);close(c2a[0]);close(c2a[1]);
    while(n>=0){
      read(a2b[0],&n,sizeof(int));
      printf("B: %d\n",n);
      n--;
      write(b2c[1],&n,sizeof(int));
    }
    close(a2b[0]);close(b2c[1]);
    exit(0);
  }
 if(fork()){
    //Sunt in C
    close(a2b[0]);close(a2b[0]);close(b2c[1]);close(c2a[0]);
    while(n>=0){
      read(b2c[0],&n,sizeof(int));
      printf("C: %d\n",n);
      n--;
      write(c2a[1],&n,sizeof(int));
    }
    close(b2c[1]);close(c2a[1]);
    exit(0);
  }
  close(a2b[1]);close(a2b[0]);close(b2c[0]);close(b2c[1]);close(c2a[0]);close(c2a[1]);
  wait(0);wait(0);wait(0);
  return 0;
}
