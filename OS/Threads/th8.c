/*
Sa se creeze 10 threaduri.
Fiecare thread afiseaza un mesaj cu numarul sau.
*/

#include<stdio.h>
#include<pthread.h>
#include<unistd.h>
#define NRT 10

void *function(void *p){
        int k=(int)p;

        printf("Eu sunt thread-ul %d\n",k);
//      sleep(1);
        return NULL;
}

int main(){
        pthread_t th[NRT];
        int i;
        for(i=0;i<NRT;i++)
                pthread_create(&th[i],NULL,function,(void*)i);
        for(i=0;i<NRT;i++)
                pthread_join(th[i],NULL);
        return 0;
}
