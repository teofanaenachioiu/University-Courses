/*
Program simplu
*/

#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/wait.h>

int main() {
  int pid;

  printf("Parinte %d\n", getpid());
  sleep(5);
  pid = fork();

  if(pid == 0) { //fiu
    printf("Fiu %d %d\n", getpid(), getppid());
    sleep(5);
    exit(0);
  }

  printf("Parinte din nou %d\n", getpid());
  sleep(10);
  wait(0);
  sleep(5);
  return 0;
}

