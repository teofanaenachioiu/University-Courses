/*
Se da un sir de numere.
Sa se construiasca un alt sir cu toate numerele ce se termina cu 3 de 0.
*/
#include<stdio.h>
#include<stdlib.h>
#include<unistd.h>

#define DIM_SIR 100000
#define NR 10

pthread_mutex_t mutex;
int sir[DIM_SIR];
int sir_nou[DIM_SIR];
int index=0;

typedef struct _PARAM{
        int *array;
        int start,stop;
}PARAM;

void *afisare(void *p){
        PARAM param=*(PARAM*)p;
//      printf("%d %d\n",param.start,param.stop);
        int i=param.start;

        while(i<param.stop){
                pthread_mutex_lock(&mutex);
                if(param.array[i]%1000==0){
//                      printf("NR: %d\n",param.array[i]);
                        sir_nou[index]=param.array[i];
                        index+=1;
                }
                i+=1;
                pthread_mutex_unlock(&mutex);
        }
        return NULL;

}
int main(){
        int i;
        pthread_t tr[DIM_SIR];
        pthread_mutex_init(&mutex);
        PARAM param[NR];
        for(i=0;i<DIM_SIR;i++)
                sir[i]=i;
        for(i=0;i<NR;i++){
                param[i].start=i*(DIM_SIR/NR);
                param[i].stop=(i+1)*(DIM_SIR/NR);
                param[i].array=sir;
                pthread_create(&tr[i],NULL,afisare,(void *)&param[i]);
        }
        for(i=0;i<NR;i++)
                pthread_join(tr[i],NULL);
        for(i=0;i<index;i++)
                printf("%d ",sir_nou[i]);
        printf("\n");
        pthread_mutex_destroy(&mutex);
        return 0;
}
