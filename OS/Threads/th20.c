/*5. Scrieti un program in care definiti un vector cu 5 elemente care contine numerele de la 1 la 5 in ordine aleatoare. Initializati o variabila globala cu 1.

Creati 5 threaduri care primesc v[i] ca si parametru si efectueaza urmatoarea operatie:

        - asteapta pana variabila globala are valoarea egala cu v[i]

        - afiseaza id-ul threadului

        - incrementeaza variabila globala cu 1*/
#include<stdio.h>
#include<stdlib.h>
#include<unistd.h>
#include<pthread.h>

#define NRT 5

pthread_cond_t cond;
pthread_mutex_t mutex;

int v[5]={2,5,1,0,3};
int index=1;

void *inc(void *p){

        int nr=(int) p;
        printf("%d\n",nr);
        pthread_mutex_lock(&mutex);
        while(index<nr){
                pthread_cond_wait(&cond,&mutex);        printf("Bucla");
        }
        index++;
//      printf("da");
        pthread_mutex_unlock(&mutex);
//      while(1){
/*              pthread_mutex_lock(&mutex);index++;
                if(index>=5)
                        pthread_cond_signal(&cond);
                pthread_mutex_unlock(&mutex);
//      }*/



        return NULL;
}

int main(){
        pthread_mutex_init(&mutex,NULL);
        pthread_cond_init(&cond,NULL);
        pthread_t th[NRT];
        int i;
        for(i=0;i<NRT;i++)
                pthread_create(&th[i],NULL,inc,(void*)v[i]);
        for(i=0;i<NRT;i++)
                pthread_join(th[i],NULL);
        pthread_mutex_destroy(&mutex);
        pthread_cond_destroy(&cond);
        printf("%d\n",index);
        return 0;
}
