/*
Se da un sir de caractere si un caracter.
Se cere sa se elimine din sir prima aparitie a caracterului.
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

void eliminaCaracter(char ch, char str []){
        int i, poz=-1;
        for(i=0;i<strlen(str) && poz==-1;i++){
                if(str[i]==ch)  poz=i;
        }
        if(poz!=-1){
                for(i=poz;i<strlen(str);i++){
                        str[i]=str[i+1];
                }
        }

}

void * deservire_client(void *arg){
        char str[100], ch, caracter;
        uint16_t i=0;
        int c=*((int*)arg);

        recv(c,&caracter,sizeof(caracter),MSG_WAITALL);
        printf("Carcaterul primit: %c\n",caracter);
        do{
                recv(c,&ch,sizeof(ch),MSG_WAITALL);
                str[i++]=ch;
        }while(ch!=0);

        printf("Sirul primit |%s|\n",str);
        eliminaCaracter(caracter,str);
        printf("Noul sir |%s|\n",str);
        send(c,str,sizeof(char)*(strlen(str)+1),0);

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
