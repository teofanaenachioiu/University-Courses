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

#define SERVERPORT 1234

int main(){
        int sockfd;
        struct sockaddr_in server;
        uint16_t nr,ind=0,copie,suma;

        sockfd=socket(AF_INET,SOCK_STREAM,0);
        if(sockfd==-1){
                printf("Eroare la crearea socketului client\n");
                exit(1);
        }

        memset(&server,0,sizeof(server));
        server.sin_port=htons(SERVERPORT);
        server.sin_family=AF_INET;
        server.sin_addr.s_addr=inet_addr("127.0.0.1");

        if(connect(sockfd,(struct sockaddr *)&server,sizeof(server))==-1){
                printf("Eroare la conectarea la server\n");
                exit(1);
        }

        //Numarul de elemente
        printf("Suma a cator numere doriti sa calculati?\n");
        printf("Raspuns: ");
        scanf("%hu",&nr);
        uint16_t elem[nr];
        copie=nr;
        nr=htons(nr);
        send(sockfd,&nr,sizeof(nr),0);

        //Elementele array-ului
        printf("Se va calcula suma a %hua numere\n",copie);
        printf("Dati numerele\n");
        while(ind<copie){
                printf("NR%d: ",ind+1);scanf("%hu",&elem[ind]);
                elem[ind]=htons(elem[ind]);
                send(sockfd,&elem[ind],sizeof(elem[ind]),0);
                ind=ind+1;
        }

        //Suma elementelor
        recv(sockfd,&suma,sizeof(suma),0);
        suma=ntohs(suma);
        printf("Suma numerelor este: %hu\n",suma);
        close(sockfd);
}
