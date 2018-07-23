/*
Pentru fiecare nume de fisier dat ca parametru in linia de comanda 
lansati cate un thread care numara aparitiile fiecarei vocale in fisier. 
Programul va afisa numarul total de aparitii pentru fiecare vocala in 
toate fisierele. Folositi un singur tablou global ce pastreaza numarul 
de aparitii per vocala.
*/

#include<stdio.h>
#include<stdlib.h>
#include<unistd.h>
#include<sys/wait.h>
#include<sys/types.h>
#include<fcntl.h>
#include<string.h>
#include<semaphore.h>
int vocale[5];
//pthread_mutex_t mutex;
sem_t sem;


void * functie(void * p){
        char * sir=(char*) p;
        FILE* f=fopen(sir,"r");
        if(f==NULL)
                {printf("Eroare!\n");
                exit(1);
                }
        else printf("Fisierul %s exista si se proceseaza datele\n",sir);

        char  buff[100];
        int ok=1,i;
        while(ok){
                fgets(buff,100,(FILE*)f);
                //printf("%s dim\n",buff);
                //pthread_mutex_lock(&mutex);
                sem_wait(&sem);
                for(i=0;i<strlen(buff);i++)
                        {
                        if(buff[i]=='a'|| buff[i]=='A')
                                vocale[0]++;
                        if(buff[i]=='e'||buff[i]=='E')
                                vocale[1]++;
                        if(buff[i]=='i'||buff[i]=='I')
                                vocale[2]++;
                        if(buff[i]=='o'||buff[i]=='O')
                                vocale[3]++;
                        if(buff[i]=='u'||buff[i]=='U')
                                vocale[4]++;
                }
                //pthread_mutex_unlock(&mutex);
                sem_post(&sem);
                if(strlen(buff)<100){
                        break;
                }
        }


        fclose(f);
        return NULL;

}

int main(int argc,char ** argv){
        //pthread_mutex_init(&mutex,NULL);
        sem_init(&sem,0,1);
        int n=argc-1;
        pthread_t th[n];
        int i;
//      printf("%d\n",argc-1);
        for(i=0;i<argc-1;i++)
                pthread_create(&th[i],NULL,functie,(void *)argv[i+1]);
        for(i=0;i<argc-1;i++)
                pthread_join(th[i],NULL);
        for(i=0;i<5;i++)
                printf("%d ",vocale[i]);
        //pthread_mutex_destroy(&mutex);
        sem_destroy(&sem);
        return 0;
}
