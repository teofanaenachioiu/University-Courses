/*
Un client trimite unui server doua siruri de caractere.
Serverul ii raspunde clientului cu caracterul care se regaseste
de cele mai multe ori pe pozitii identice in cele doua siruri
si cu numarul de aparitii ale acestui caracter.
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

char caracter(char sir1[100],char sir2[100]){
        int caract[26]={0};
        int i,nr=strlen(sir1);
        if(strlen(sir2)<nr)nr=strlen(sir2);
        for(i=0;i<nr;i++)
                if(sir1[i]==sir2[i]){
                        caract[sir1[i]-97]++;
                }
        int max=0,poz=0;
        for(i=0;i<26;i++){
                if(caract[i]>max){
                        max=caract[i];
                        poz=i;
                }
        }
        return 'a'+poz;
}

void * deservire_client(void * arg){
        int c=*((int*)arg);
        int i=0;
        char ch,sir1[100],sir2[100];
        do{
                recv(c,&ch,sizeof(ch),0);
                sir1[i++]=ch;
        }while(ch!=0);
        i=0;
        do{
                recv(c,&ch,sizeof(ch),0);
                sir2[i++]=ch;
        }while(ch!=0);
        ch=caracter(sir1,sir2);

        send(c,&ch,sizeof(ch),0);
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
