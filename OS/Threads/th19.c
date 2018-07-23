/*
Intelegerea conceptului de bariera.
*/

#include<stdio.h>
#include<stdlib.h>
#include<unistd.h>
#include<pthread.h>

#define NRT 100

pthread_barrier_t bar;

void *functie(void *p){
        int nr=(int)p;
        printf("A intrat thread-ul %d\n",nr);
        pthread_barrier_wait(&bar);
        printf("A terminat %d\n",nr);
        return NULL;
}

int main(){
        pthread_barrier_init(&bar,NULL,NRT);
        pthread_t th[NRT];
        int i;

        for(i=0;i<NRT;i++)
                pthread_create(&th[i],NULL,functie,(void*)i);
        for(i=0;i<NRT;i++)
                pthread_join(th[i],NULL);

        pthread_barrier_destroy(&bar);
        return 0;
}
