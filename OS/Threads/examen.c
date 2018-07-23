/*
Sa se scrie un program care citeste un numar N de la tastatura 
si creeaza doua threaduri (pot fi descrise de doua functii diferite
sau de aceeasi functie). Unul dintre ele va genera un numar par 
aleator pe care sa il adauge intr-un sir primit ca parametru de la 
threadul principal, iar celalalt va face acelasi lucru, generand in 
schimb numere impare. Intre cele doua threaduri trebuie implementata 
o sincronizare care sa asigure ca in sir se adauga alternativ cate un 
numar par, respectiv impar, pana la lungimea maxima N.
*/

#include<stdio.h>
#include<stdlib.h>
#include<unistd.h>
#include<pthread.h>

int sir[100];
int n;
int indexx=0;

pthread_mutex_t mutex;
pthread_barrier_t bar;


void* par(void * p){
        while(indexx<n){
        int nrp=rand()%100/2;
        //printf("%d\n",nrp);
        pthread_barrier_wait(&bar);
        pthread_mutex_lock(&mutex);
        if(indexx<n)
        {
                if(indexx%2==0)
                        sir[indexx]=nrp;
                else sir[indexx]=nrp+1;
                indexx++;

        }
        pthread_mutex_unlock(&mutex);
        }
        return NULL;
}

void * fct(void *p){
        while(indexx<n){
//      pthread_barrier_wait(&bar);
        pthread_mutex_lock(&mutex);
        if(indexx<n){
                if(indexx%2==1)
                        sir[indexx]=rand()%100*2+1;
                else sir[indexx]=rand()%100*2;
                indexx++;
        }
        pthread_mutex_unlock(&mutex);
        }
        return NULL;
}


int main(){
        pthread_t th1, th2;
        pthread_mutex_init(&mutex,NULL);
//      pthread_barrier_init(&bar,NULL,2);

        printf("N=");scanf("%d",&n);

        pthread_create(&th1,NULL,fct,sir);
        pthread_create(&th2,NULL,fct,sir);

        pthread_join(th1,NULL);
        pthread_join(th2,NULL);

        pthread_mutex_destroy(&mutex);
//      pthread_barrier_destroy(&bar);

        for(int i=0;i<n;i++)
                printf("%d ",sir[i]);
        return 0;
}
