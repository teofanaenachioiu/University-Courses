/*
Suma 1+2+3+4
*/

#include<stdio.h>
#include<stdlib.h>
#include<unistd.h>
#include<sys/types.h>
#include<sys/wait.h>
int main(){
  int a[]={1,2,3,4};
  int p[2];
  pipe(p);
  if (fork()){
    close(p[0]);
    a[2]+=a[3];
    write(p[1],&a[2],sizeof(int));
    close(p[1]);
    exit(0);
  }
 // wait(0);
  close(p[1]);
  read(p[0],&a[2],sizeof(int));
  close(p[0]);
  a[0]+=a[1];
  wait(0);
  a[0]+=a[2];
  printf("Sum=%d\n",a[0]);
  return 0;
}



