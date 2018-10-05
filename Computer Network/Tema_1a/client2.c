/*
Un client timite unui server un sir de numere.
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
        uint16_t nr=-1;
        char sir[200];

        sockfd=socket(AF_INET, SOCK_STREAM,0);
        if(sockfd==-1){
                printf("Eroare la crearea socketului client.\n");
                exit(1);
        }
        memset(&server,0,sizeof(server));
        server.sin_port=htons(SERVERPORT);
        server.sin_family=AF_INET;
        server.sin_addr.s_addr=inet_addr("127.0.0.1");

        if(connect(sockfd,(struct sockaddr*)&server,sizeof(server))==-1){
                printf("Eroare la conectarea la server.\n");
                exit(1);
        }
        printf("Dati sirul de caractere: ");
        gets(sir);
        send(sockfd,sir,sizeof(sir),0);
        recv(sockfd,&nr,sizeof(nr),0);
        nr=ntohs(nr);
        printf("Numarul de spatii: %hu\n",nr);
        close(sockfd);
}
