/*
Se da un sir de numere. 
Sa se construiasca un alt sir cu toate numerele divizibile la un numar dat.
*/
#include<stdio.h>
#include<pthread.h>

#define DIM 100
#define NRT 10
#define DIV 5

pthread_mutex_t mutex;
int nrd=0;
int step=DIM/NRT;
int array[DIM];
int new[DIM];

void* numarare(void *p){
        int k=(int)p;
        //printf("[%d,%d]\n",k*step,(k+1)*step-1);
        int start,end,index;
        start=k*step;
        end=(k+1)*step;
        index=start;
        while(index<end){
                if(array[index]%DIV==0){
                        pthread_mutex_lock(&mutex);
                        new[nrd]=array[index];
                        nrd++;
                        pthread_mutex_unlock(&mutex);
                        }
                index++;
        }
        return NULL;
}

int main(){
        pthread_t th[NRT];
        pthread_mutex_init(&mutex,NULL);
        int i;
        for(i=0;i<DIM;i++)
                array[i]=i+1;
        for(i=0;i<NRT;i++){
                pthread_create(&th[i],NULL,numarare,(void*)i);
        }
        for(i=0;i<NRT;i++)
                pthread_join(th[i],NULL);
        printf("NR: %d\n",nrd);
        for(i=0;i<nrd;i++)
                printf("%d ",new[i]);
        printf("\n");
        pthread_mutex_destroy(&mutex);
        return 0;

}
