/*
Sa se creeze 100 de threaduri.
Fiecare thread afiseaza numarul sau. 

Contraexemplu de implementare: se afiseaza "Eu sunt thread-ul 0"
*/

#include <stdio.h>
#include <pthread.h>

#define MAX_NUM 100                     // maximum number of threads

//
// The thread start routine
//
void* f(void* a)
{
  int k = *(int*)a;
  printf("Eu sunt thread-ul %d\n", k);

  return NULL;
}



int main(int argc, char* argv[])
{
  pthread_t t[MAX_NUM];                 // an array of threads

  int i;
  for (i = 0; i < MAX_NUM; i++)
  {
    pthread_create(&t[i], NULL, f,&i); // create a thread
  }

  for (i = 0; i < MAX_NUM; i++)
  {
    pthread_join(t[i], NULL);           // wait for each thread to finish
  }

  return 0;
}
