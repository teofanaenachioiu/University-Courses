/*
Problema biletelor de avion cu RWLOCK
*/
#include<stdlib.h>
#include<stdio.h>
#include<unistd.h>

#define R 1000 //readerii
#define W 10 //writerii

pthread_rwlock_t lock;
int locuri=100; //numarul de locuri din avian
float pret=75.0f;

void *consulta(void *p){
        int n=(int)p;
        pthread_rwlock_rdlock(&lock);//blochez citirea. doar pentru threadul curent
        printf("Eu sunt reader-ul %d. Nr locuri=%d, pret=%f\n",n,locuri,pret*(2-locuri/100.0f));
        pthread_rwlock_unlock(&lock);
        return NULL;
}

void *cumpara(void *p){
        int n=(int)p;
        pthread_rwlock_wrlock(&lock);//blochez scrierea. doar pentru threadul curent
        printf("Eu sunt writer-ul %d. Nr locuri=%d, pret=%f\n",n,locuri,pret*(2-locuri/100.0f));
        locuri--;
        pthread_rwlock_unlock(&lock);
        return NULL;
}

int main(){
        int i;
        pthread_t tr[R],tw[W];
        pthread_rwlock_init(&lock);
        for(i=0;i<R;i++){
                pthread_create(&tr[i],NULL,consulta,(void *)i);
        }
        for(i=0;i<W;i++){
                pthread_create(&tw[i],NULL,cumpara,(void*)i);
        }
        for(i=0;i<R;i++)
                pthread_join(tr[i],NULL);
        for(i=0;i<W;i++)
                pthread_join(tw[i],NULL);
        pthread_rwlock_destroy(&lock);
        return 0;

}
