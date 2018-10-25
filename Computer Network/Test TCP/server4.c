/*
Un client trimite unui server doua siruri de numere.
Serverul va returna clientului sirul de numere
care se regaseste in cel de-al doilea sir dar nu se regasesc in primul.
*/

#include <stdio.h>
#include <stdlib.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <string.h>
#include <arpa/inet.h>
#include <fcntl.h>
#include <unistd.h>
#include <pthread.h>
#include <string.h>

#define MYPORT 1234

int apartine(uint16_t el, uint16_t array[],uint16_t n){
        int ok=0;
        for(uint16_t i=0;i<n&&ok==0;i++){
                if(array[i]==el)
                        ok=1;
        }
        return ok;
}

void * deservire_client(void *arg){
        int c=*((int*)arg);
        uint16_t n1,n2;
        recv(c,&n1,sizeof(n1),0);
        n1=ntohs(n1);
        uint16_t el,array[n1];
        for(uint16_t i=0;i<n1;i++){
                recv(c,&el,sizeof(el),0);
                array[i]=ntohs(el);
        }
        recv(c,&n2,sizeof(n2),0);
        n2=ntohs(n2);
        uint16_t sir[n2],nr=0;
        for(uint16_t i=0;i<n2;i++){
                recv(c,&el,sizeof(el),0);
                el=ntohs(el);
                if(!apartine(el,array,n1)){
                        sir[nr++]=el;
                }
        }
        uint16_t nn=nr;
        nn=htons(nr);
        send(c,&nn,sizeof(nr),0);
        for(uint16_t i=0;i<nr;i++){
                el=htons(sir[i]);
                send(c,&el,sizeof(el),0);
        }
        close(c);
        pthread_exit(NULL);
}

int main(){
        int s;
        struct sockaddr_in server,client;
        int c, l;

        s=socket(AF_INET,SOCK_STREAM,0);
        if(s<0){
                printf("Eroare la crearea socketului server\n");
                return 1;
        }

        memset(&server,0,sizeof(server));
        server.sin_port=htons(MYPORT);
        server.sin_family=AF_INET;
        server.sin_addr.s_addr=INADDR_ANY;

        if(bind(s,(struct sockaddr*)&server,sizeof(server))<0){
                printf("Eroare la bind\n");
                return 1;
        }

        listen(s,5);

        l=sizeof(client);
        memset(&client,0,sizeof(client));

        pthread_t tid[30];
        int i=0;

        while(1){
                c=accept(s,(struct sockaddr *)&client,&l);
                if(pthread_create(&tid[i++],NULL,deservire_client,&c)!=0){
                        printf("Eroare la crearea thread-ului\n");
                }

                if(i>=30){
                        i=0;
                        while(i<30){
                                pthread_join(tid[i++],NULL);
                        }
                        i=0;
                }
        }
}
