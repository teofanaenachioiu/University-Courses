/*
Scrieti un program care determina valoarea maxima dintr-un sir 
de 100 000 de elemente. Sirul va fi parcurs folosind 10 thread-uri.
*/

#include<stdio.h>
#include<stdlib.h>
#include<unistd.h>
#include<semaphore.h>

#define NRT 10
#define SIZE 100000

sem_t sem;

typedef struct _PARAM{
        int start,end;
        int *array;
}PARAM;

int sir[SIZE];
int max=0;

void * functie(void * p){
        PARAM par=*(PARAM *) p;
//      printf("%d\n",par.start);
        int k,maxi=0;
        for(k=par.start;k<par.end;k++)
                if(par.array[k]>maxi)
                        maxi=par.array[k];
        sem_wait(&sem);
        if(maxi>max)
                max=maxi;
        sem_post(&sem);
        return NULL;
}

int main(){
        sem_init(&sem,0,1);
        int i;
        PARAM param[NRT];
        pthread_t th[NRT];
        for(i=0;i<SIZE;i++)
                sir[i]=rand()%100;
        for(i=0;i<NRT;i++)
        {
                param[i].array=sir;
                param[i].start=i*(SIZE/NRT);
                param[i].end=(i+1)*(SIZE/NRT);
        }
        for(i=0;i<NRT;i++){
                pthread_create(&th[i],NULL,functie,(void*)&param[i]);
        }
        for(i=0;i<NRT;i++){
                pthread_join(th[i],NULL);
        }
        printf("max=%d\n",max);
        sem_destroy(&sem);
        return 0;
}
