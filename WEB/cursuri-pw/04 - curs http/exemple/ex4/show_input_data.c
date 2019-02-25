#include <stdio.h>
#include <stdio.h>

int main() {
  char line[1000];
  printf("Content-type: text/html\n\n");
  fgets(line, 1000, stdin);
  printf("Am primit de la browser %s", line);
}
