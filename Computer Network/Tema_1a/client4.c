/*
Un client trimite unui server doua siruri de caractere ordonate.
Serverul va interclasa cele doua siruri si va returna clientului sirul rezultat interclasat.
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
        char sir1[100],sir2[100],sir[200];

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
        printf("Dati sirul 1 ordonat: ");
        gets(sir1);
        send(sockfd,sir1,sizeof(sir1),0);
        printf("Dati sirul 2 ordonat: ");
        gets(sir2);
        send(sockfd,sir2,sizeof(sir2),0);
        recv(sockfd,sir,sizeof(sir),0);
        printf("Interclasarea sirurilor: %s\n",sir);
        close(sockfd);
}
