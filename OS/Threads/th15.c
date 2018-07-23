/*
Sa se scrie un program care genereaza pe 4 thread-uri distincte 
1000 de numere intregi pe care le introduce intr-un vector global. 
Fiecare numar generat va fi introdus de thread-ul care l-a generat 
pe pozitia corecta in vector, astfel incat la orice moment vectorul 
sa fie sortat. Thread-ul principal (main) va afisa sirul final;

NU FUNCTIONEAZA!!!
*/

#include<stdio.h>
#include<pthread.h>

#define NRT 2
#define DIM 10

int array[DIM];
int size=0;
pthread_mutex_t mutex;

void muta(int array[DIM],int elem){
        int i, k=size;

//              printf("Da\n");
                while(array[k-1]<array[k-2]){
//                      printf("Intra\n");
                        array[k]=array[k-1];
                        k--;
                        }
//               for(i=0;i<size;i++)
  //                    printf("%d ",array[i]);
//              printf("\n");
                array[k]=elem;

}

void * functie(void *p){
        int start=(int)p;
        start=start*(DIM/NRT);
        int end=(start+1)*(DIM/NRT);
        int i=start;
        int elem;
        pthread_mutex_lock(&mutex);
        while(i<end){
                elem=rand()%100;
                array[size]=elem;
                size++;
                i++;

        //      muta(array,elem);
                int indee=size;
                while(array[indee-1]<array[indee-2])
                {
                        array[indee-1]=array[indee-2];
                        indee--;
                }
                array[indee]=elem;
        }
        pthread_mutex_unlock(&mutex);
        return NULL;
}

int main(){
        pthread_t th[NRT];
        pthread_mutex_init(&mutex,NULL);
        int i;
        for(i=0;i<NRT;i++)
                pthread_create(&th[i],NULL,functie,(void*)i);
        for(i=0;i<NRT;i++)
                pthread_join(th[i],NULL);
        for(i=0;i<DIM;i++)
                printf("%d ",array[i]);
        pthread_mutex_destroy(&mutex);
        printf("\n");
        return 0;
}
