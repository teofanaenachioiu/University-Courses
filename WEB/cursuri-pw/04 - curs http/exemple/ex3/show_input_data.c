#include <stdio.h>
#include <stdlib.h>

int main() {
  char line[1000];
  printf("Content-type: text/html\n\n");
  printf("Am primit de la browser query string-ul %s", getenv("QUERY_STRING"));
}
