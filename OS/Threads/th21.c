/*
Problema cititorilor si scriitorilor.
*/

#include<pthread.h>
#include<stdlib.h>
#include<unistd.h>
#include<stdio.h>
#define S 5
#define C 5
#define CSLEEP 2
#define SSLEEP 3

pthread_t tid[C+S];
int c[C], s[S], nt[C+S];
pthread_mutex_t mutcond, exclusafis;
pthread_cond_t cond;
int cititori;

void afiseaza(){
        int i;
        pthread_mutex_lock(&exclusafis);
        for(i=0;i<C;i++)
                printf("Cititor %d stare %d\n",i,c[i]);
        for(i=0;i<S;i++)
                printf("Scriitor %d stare %d\n",i,s[i]);
        fflush(stdout);
        pthread_mutex_unlock(&exclusafis);
}

void *cititor(void * nrc){
        int indc=*(int*)nrc;
        for(;;){
                c[indc]=-1;//Asteapta sa citeasca

                pthread_mutex_lock(&mutcond);
                cititori++;
                c[indc]=0;//Citeste
                afiseaza();
                pthread_mutex_unlock(&mutcond);

                sleep(1+rand()%CSLEEP);
                c[indc]=-2;//A citit si doarme

                pthread_mutex_lock(&mutcond);
                cititori--;
                pthread_cond_signal(&cond);
                pthread_mutex_unlock(&mutcond);

                sleep(1+rand()%CSLEEP);
        }
}

void * scriitor(void * nrs){
        int inds=*(int*)nrs;
        for(;;){
                s[inds]=-1;//Asteapta sa scrie

                pthread_mutex_lock(&mutcond);
                for(;cititori>0;) pthread_cond_wait(&cond,&mutcond);
                s[inds]=0;//Scrie
                afiseaza();
                sleep(1+rand()%SSLEEP);
                s[inds]=-2;//Doarme
                pthread_mutex_unlock(&mutcond);

                sleep(1+rand()%SSLEEP);
        }
}

int main(){
        pthread_mutex_init(&mutcond,NULL);
        pthread_mutex_init(&exclusafis,NULL);
        pthread_cond_init(&cond,NULL);
        int i;
        for(i=0;i<C;c[i]=-3,nt[i]=i,i++);//Cititor neactivat
        for(i=0;i<S;s[i]=-3,nt[i+C]=i,i++);

        for(i=0;i<C;i++)
                pthread_create(&tid[i],NULL,cititor,&nt[i]);
        for(i=C;i<S+C;i++)
                pthread_create(&tid[i],NULL,scriitor,&nt[i]);
        for(i=0;i<S+C;i++)
                pthread_join(tid[i],NULL);
        pthread_cond_destroy(&cond);
        pthread_mutex_destroy(&exclusafis);
        pthread_mutex_destroy(&mutcond);
        return 0;
}
