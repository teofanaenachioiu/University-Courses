/*
Avem 3 thread-uri:
	-2 incrementeaza un contor
	-1 asteapta pana cand contorul e 15
*/
#include<stdio.h>
#include<stdlib.h>
#include<pthread.h>
#include<unistd.h>

#define NRT 3
#define TCOUNT 10//de cate ori incrementam contorul
#define COUNT_LIMIT 15

int count=0;

pthread_mutex_t mutex;

pthread_cond_t co;

void * inc_contor(void *t){
        int i;
        for(i=0;i<TCOUNT;i++){
                pthread_mutex_lock(&mutex);
                count++;
                printf("[i] Count=%d\n",count);
                if(COUNT_LIMIT==count){
                        pthread_cond_signal(&co);//semnalizam indeplinirea contitiei
                        printf("[i] Signal sent\n");
                }
                pthread_mutex_unlock(&mutex);
                sleep(1);//simulaza o activitatea. Ma ajuta sa vad cum se comporta threadurile
        }
        return NULL;
}

void * watch_count(void * p){
        printf("[w] Started\n");
        pthread_mutex_lock(&mutex);
        while(count<COUNT_LIMIT){
                printf("[w] Waiting signal\n");
                pthread_cond_wait(&co,&mutex);//pune threadul in asteptare si da unlock la mutex, pentru ca celelalte threaduri sa-l poata folosi
                printf("[w] Cond signal received\n");
        }
        //cand treadul se trezeste, din cauza semnalului, threadul asta primeste mutextul.
        printf("[w] count=%d!!!\n",count);
        pthread_mutex_unlock(&mutex);
        return NULL;
}

int main(){
        pthread_t thr[NRT];

        pthread_mutex_init(&mutex,NULL);
        pthread_cond_init(&co,NULL); //init var conditionala

        pthread_create(&thr[0],NULL,watch_count,NULL);
        pthread_create(&thr[1],NULL,inc_contor,NULL);
        pthread_create(&thr[2],NULL,inc_contor,NULL);

        for(int i=0;i<NRT;i++)
                pthread_join(thr[i],NULL);

        printf("[MAIN] Done\n");

        pthread_mutex_destroy(&mutex);
        pthread_cond_destroy(&co); //distruge var conditionala
        return 0;
}
