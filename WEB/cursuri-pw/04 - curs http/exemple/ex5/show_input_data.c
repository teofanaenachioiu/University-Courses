#include <stdio.h>

int main() {
  char line[1000];
  printf("Content-type: text/html\n\n");
  while (! feof(stdin)) {
    fgets(line, 1000, stdin);
    if (! feof(stdin))
      printf("%s<br/>", line);
  }
}
