/*
Un client trimite unui server un sir de numere.
Serverul va returna clientului suma numerelor primite.
*/

#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>
#include <stdlib.h>

#define MYPORT 1234
#define NRL 5

int main(){
        int sockfd, newfd;
        struct sockaddr_in server, client;

        sockfd=socket(AF_INET, SOCK_STREAM,0);
        if(sockfd==-1){
                printf("Eroare la crearea socketului server\n");
                exit(1);
        }

        memset(&server,0,sizeof(server));
        server.sin_port=htons(MYPORT);
        server.sin_family=AF_INET;
        server.sin_addr.s_addr=INADDR_ANY;

        if(bind(sockfd,(struct sockaddr *)&server, sizeof(server))==-1){
                printf("Eroare la bind\n");
                exit(1);
        }

        listen(sockfd,NRL);
        int l=sizeof(client);
        memset(&client,0,sizeof(client));

        while(1){
                uint16_t ind=0,nr,suma=0;
                newfd=accept(sockfd,(struct sockaddr *)&client,&l);
                printf("S-a conectat un client\n");
                if(newfd==-1) printf("Client neacceptat\n");
                recv(newfd,&nr,sizeof(nr),MSG_WAITALL);
                nr=ntohs(nr);
                uint16_t elem[nr];
                while (ind<nr){
                        recv(newfd,&elem[ind],sizeof(elem[ind]),0);
                        elem[ind]=ntohs(elem[ind]);
                        suma=suma+elem[ind];
                        ind++;
                }
                suma=htons(suma);
                send(newfd,&suma,sizeof(suma),0);
                close(newfd);
                printf("Sfarsitul deservirii clientului\n");
        }
}
