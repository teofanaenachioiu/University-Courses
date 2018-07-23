/*
Se citeste un numar natural n<10 si n perechi de numere. 
Sa se determine cate perechi au suma numar prim. 
Se vor folosi n thread-uri.
*/

#include<stdio.h>
#include<pthread.h>
#include<stdlib.h>
#define NRT 10


typedef struct _PARAM{
        int x,y;
}PARAM;

int nr=0;

pthread_mutex_t mutex;

int prim(int nr){
        int d=2;
        if(nr==1||nr==0) return 0;
        while(d<nr/2){
                if(nr%d==0)
                         return 0;
                d++;
        }
        return 1;
}

void * function(void *p){
//      printf("Intra\n");
        PARAM param=*(PARAM*)p;
        int suma=param.x+param.y;
//      printf("Suma: %d",suma);
        if(prim(suma)==1)
                {
                        pthread_mutex_lock(&mutex);
                        nr++;
                        pthread_mutex_unlock(&mutex);
                }
        return NULL;
        }


int main(){
        int n;
        //PARAM param[NRT];
        //pthread_t th[NRT];
        pthread_mutex_init(&mutex,NULL);
        printf("n=");scanf("%d",&n);
        if(n>=10) {printf("Ati introdus un numar mai mare sau egal cu 10\n");exit(0);}
        pthread_t th[n];
        PARAM param[n];
        int i;
        for(i=0;i<n;i++){
                printf("Element x[%d]=",i+1);scanf("%d",&param[i].x);
                printf("Element y[%d]=",i+1);scanf("%d",&param[i].y);
                pthread_create(&th[i],NULL,function,(void*)&param[i]);
        }
        for(i=0;i<n;i++)
                pthread_join(th[i],NULL);
        printf("Numarul de perechi cu suma numar prim: %d\n",nr);
        pthread_mutex_destroy(&mutex);
        return 0;
}