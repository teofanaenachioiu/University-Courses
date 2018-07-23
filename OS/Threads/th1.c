/*
Suma numerelor dintr-un sir.
Implementare fara mutex (=> datele nu sunt protejate!!)
*/

#include<stdio.h>
#include<stdlib.h>
#include<pthread.h>

#define SIZE 10000

typedef struct _PARAM{
        int *array;
        int ind_start,ind_stop;
}PARAM;

int global_sum=0;

void * arr_sum(void * p){
        PARAM param;
        int i,local_sum=0;
        param=*(PARAM*)p;
        for(i=param.ind_start;i<param.ind_stop;i++){
                local_sum+=param.array[i];
        }
        global_sum+=local_sum;
        return 0;
}

int main(){
        int array[SIZE];
        pthread_t th;
        PARAM param;
        for(int i=0;i<SIZE;i++){
                array[i]=1;
        }
        param.array=array;
        param.ind_start=0;
        param.ind_stop=SIZE;
        pthread_create(&th,NULL,&arr_sum,(void*)&param);
        pthread_join(th,NULL);
        printf("Global sum: %d\n",global_sum);
        return 0;
}
