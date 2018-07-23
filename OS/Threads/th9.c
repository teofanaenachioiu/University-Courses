/*
Problema biletelor de avion (cu MUTEX).
*/

#include<stdio.h>
#include<pthread.h>

#define R 10
#define W 3

int locuri=100;
float pret=75.0f;

pthread_mutex_t mutex;

void* consulta(void *p){
        int k=(int)p;
        pthread_mutex_lock(&mutex);
        printf("Sunt vizitatorul %d: nr locuri dis %d, pret %f\n",k,locuri,pret*(2-locuri/100.0f));
        pthread_mutex_unlock(&mutex);
        return NULL;
}

void* cumpara(void *p){
        int k=(int)p;
        pthread_mutex_lock(&mutex);
        printf("Sunt cumparatorul %d: locul meu %d, pret %f\n",k,locuri,pret*(2-locuri/100.0f));
        locuri--;
        pthread_mutex_unlock(&mutex);
        return NULL;
}

int main(){
        pthread_t thw[W], thr[R];
        pthread_mutex_init(&mutex,NULL);
        int i;
        for(i=0;i<R;i++)
                pthread_create(&thr[i],NULL,consulta,(void*)i);

        for(i=0;i<W;i++)
                pthread_create(&thw[i],NULL,cumpara,(void*)i);

        for(i=0;i<W;i++)
                pthread_join(thw[i],NULL);

        for(i=0;i<R;i++)
                pthread_join(thr[i],NULL);
        pthread_mutex_destroy(&mutex);
        return 0;
}
