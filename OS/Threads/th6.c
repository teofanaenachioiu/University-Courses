/*
Sa se creeze doua thread-uri.
Fiecare thread afiseaza cate un mesaj.
*/

#include<stdio.h>
#include<pthread.h>
#include<stdlib.h>
#include<unistd.h>
void *function(void * msg){
        char *m;
        m=(char*)msg;
        printf("%s\n",m);
        return 0;
}

int main(){
        pthread_t t1,t2;
        const char* msg1="Thread 1";
        const char* msg2="Thread 2";
        pthread_create(&t1,NULL,function,(void*)msg1);
        pthread_create(&t2,NULL,function,(void*)msg2);
        pthread_join(t1,NULL);
        pthread_join(t2,NULL);
        return 0;
}
