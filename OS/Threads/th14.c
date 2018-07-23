#include<stdio.h>
#include<pthread.h>

#define DIM 9
#define NRT 3

pthread_mutex_t mutex;

int array[DIM];
int nrp=0;
int indexx=0;

int prim(int n){
        int d=2;
        while(d<n/2)
                if(n%d==0) return 0;
        return 1;
}

void * functie(void *p){
        int k=(int)p;
//      pthread_mutex_lock(&mutex);
        int j,start,end;
        start=k*DIM/NRT;
        end=(k+1)*DIM/NRT;
        j=start;
        printf("%d %d\n",start,end);
        while(j<end){

                if(prim(array[j])==1)
                        {pthread_mutex_lock(&mutex);
//                      printf("%d\n",array[j]);
                        nrp++;
                        pthread_mutex_unlock(&mutex);
                        }
                printf("Nu mai iese\n");
                j++;

        }
        return NULL;

}

int main(){
        pthread_t th[NRT];
        int i;
        pthread_mutex_init(&mutex,NULL);
        for(i=0;i<DIM;i++){
                printf("el[%d]=",i);scanf("%d",&array[i]);
        }
        for(i=0;i<NRT;i++)
                pthread_create(&th[i],NULL,functie,(void*)i);
        for(i=0;i<NRT;i++)
                pthread_join(th[i],NULL);
        printf("Nr prime: %d\n",nrp);
        pthread_mutex_destroy(&mutex);
        return 0;
}
