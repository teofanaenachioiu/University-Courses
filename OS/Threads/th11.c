/*
Problema biletelor de avion (cu SEMAFOARE).
*/
#include<stdio.h>
#include<pthread.h>
#include<semaphore.h>

#define R 10
#define W 3

int locuri=100;
float pret=75.0f;

sem_t sem;

void* consulta(void *p){
        int k=(int)p;
        sem_wait(&sem);
        printf("Sunt vizitatorul %d: nr locuri dis %d, pret %f\n",k,locuri,pret*(2-locuri/100.0f));
        sem_post(&sem);
        return NULL;
}
void* cumpara(void *p){
        int k=(int)p;
        sem_wait(&sem);
        printf("Sunt cumparatorul %d: locul meu %d, pret %f\n",k,locuri,pret*(2-locuri/100.0f));
        locuri--;
        sem_post(&sem);
        return NULL;
}
int main(){
        pthread_t thw[W], thr[R];
        sem_init(&sem,0,1);
        int i;
        for(i=0;i<R;i++)
                pthread_create(&thr[i],NULL,consulta,(void*)i);
        for(i=0;i<W;i++)
                pthread_create(&thw[i],NULL,cumpara,(void*)i);
        for(i=0;i<W;i++)
                pthread_join(thw[i],NULL);
        for(i=0;i<R;i++)
                pthread_join(thr[i],NULL);
        sem_destroy(&sem);
        return 0;
}
