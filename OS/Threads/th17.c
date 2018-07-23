
#include<stdio.h>
/*
Determinati cate argumente ale liniei de comanda sunt palindroame. 
Fiecare argument va fi parcurs pe cate un thread.
*/

#include<stdlib.h>
#include<unistd.h>
#include<sys/types.h>
#include<sys/wait.h>
#include<string.h>
#include<semaphore.h>
int nrP=0;
sem_t sem;
void * functie(void *p){
        char * sir=(char *)p;
        char copie[100];
        int nr=0;
        for(int i=strlen(sir)-1;i>=0;i--)
                {copie[nr++]=sir[i];

        }
//      printf("%s - %s\n",sir,copie);
        if(strcmp(sir,copie)==0)
                {
                sem_wait(&sem);
                nrP++;
                sem_post(&sem);
                }
        return NULL;
}

int main(int argc, char ** argv){
        sem_init(&sem,0,1);
        int i,n=argc-1;
        pthread_t th[n];
        for(i=0;i<n;i++)
                pthread_create(&th[i],NULL,functie,(void*)argv[i+1]);
        for(i=0;i<n;i++)
                pthread_join(th[i],NULL);
        sem_destroy(&sem);
        printf("Arg palindrom: %d\n",nrP);
        return 0;
}
