#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>

int main() {
  int a2b, b2a, n=5;

  a2b = open("fa2fb", O_WRONLY);
  b2a = open("fb2fa", O_RDONLY);
  printf("Pe cai!\n");
  write(a2b, &n, sizeof(int));
  while(n > 0) {
    read(b2a, &n, sizeof(int));
    printf("A %d\n", n);
    n--;
    write(a2b, &n, sizeof(int));
  }
  close(b2a);
  close(a2b);

  return 0;
}
