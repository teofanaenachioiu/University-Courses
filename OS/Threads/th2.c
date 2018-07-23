/*
Suma numerelor dintr-un sir.
Implementare cu mutex (=> datele protejate!!)
*/
#include<stdio.h>
#include<stdlib.h>
#include<pthread.h>

#define SIZE 10000
#define NR_THR 4

typedef struct _PARAM{
        int *array;
        int ind_start,ind_stop;
}PARAM;

pthread_mutex_t mutex;//declaram mutexul.

int global_sum=0;

void * arr_sum(void * p){
        PARAM param;
        int i,local_sum=0;
        param=*(PARAM*)p;
        for(i=param.ind_start;i<param.ind_stop;i++){
                local_sum+=param.array[i];
        }
        pthread_mutex_lock(&mutex);
        global_sum+=local_sum;//expresie comuna. au acces toate threadurile. trebuie protejat
        pthread_mutex_unlock(&mutex);
        return 0;
}

int main(){
        pthread_mutex_init(&mutex,NULL);//initializare mutex
        int array[SIZE];
        pthread_t th[NR_THR+1];//creem mai multe threaduri
        PARAM param[NR_THR+1];//trebuie vector
        for(int i=0;i<SIZE;i++){
                array[i]=1;
        }
        for(int k=0;k<NR_THR;k++){
                param[k].array=array;
                param[k].ind_start=k*SIZE/NR_THR;
                param[k].ind_stop=(k+1)*SIZE/NR_THR;
                pthread_create(&th[k],NULL,&arr_sum,(void*)&param[k]);//creem fiecare thread
        }
        for(int j=0;j<NR_THR;j++)
                pthread_join(th[j],NULL);//unire threaduri
        printf("Global sum: %d\n",global_sum);

        pthread_mutex_destroy(&mutex);//distrugere mutex
        return 0;
}
