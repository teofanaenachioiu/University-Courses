#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

int main() {
  int g2s[2], s2h[2];

  pipe(g2s); pipe(s2h);
  if(fork() == 0) {
    close(g2s[0]); close(s2h[0]); close(s2h[1]);
    dup2(g2s[1], 1);
    execlp("grep", "grep", "^s", "/etc/passwd", NULL);
    exit(1);
  }

  if(fork() == 0) {
    close(g2s[1]); close(s2h[0]);
    dup2(g2s[0], 0);
    dup2(s2h[1], 1);
    execlp("sort", "sort", NULL);
    exit(1);
  }

  if(fork() == 0) {
    close(g2s[0]); close(g2s[1]); close(s2h[1]);
    dup2(s2h[0], 0);
    execlp("head", "head", "-n", "2", NULL);
    exit(1);
  }

  close(g2s[0]); close(g2s[1]); close(s2h[0]); close(s2h[1]);
  wait(0); wait(0); wait(0);
  return 0;
}

