Sa se implementeze in C un proces parinte care creeaza trei procese
fiu A, B si C. A trimite lui B valaore 5, B decrementeaza sii ii
trimite lui C, C decrementeaza si ii trimite lui A si tot
asa. Fiecare proces se termina cand primeste o valoare <= 0.

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>

int main() {
  int a2b, b2a, n=1;

  a2b = open("fa2fb", O_RDONLY);
  b2a = open("fb2fa", O_WRONLY);
  printf("Pe cai!\n");
  while(n > 0) {
    read(a2b, &n, sizeof(int));
    printf("B %d\n", n);
    n--;
    write(b2a, &n, sizeof(int));
  }
  close(b2a);
  close(a2b);
  
  return 0;
}