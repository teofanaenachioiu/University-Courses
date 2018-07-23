/*
Sa se creeze 100 de thread-uri ce incrementeaza o variabila globala.
Afisati rezultatul.
*/

#include<stdio.h>
#include<pthread.h>

int n=0;
pthread_mutex_t mutex;

void *f(void *p){
        int i;
        for(i=0;i<10;i++){
                pthread_mutex_lock(&mutex);
                n++;
                pthread_mutex_unlock(&mutex);
        }
        return 0;
}

int main(){
        pthread_t th[100];
        pthread_mutex_init(&mutex,NULL);
        int i;
        for(i=0;i<100;i++)
                pthread_create(&th[i],NULL,f,NULL);
        for(i=0;i<100;i++)
                pthread_join(th[i],NULL);
        printf("%d\n",n);

        pthread_mutex_destroy(&mutex);
        return 0;
}
