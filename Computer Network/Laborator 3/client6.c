/*
Un client trimite unui server un sir de caractere si un caracter.
Serverul va returna clientului toate pozitiile pe care caracterul primit se regaseste in sir.

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
        int sockfd,cmd=6;
        uint16_t poz,npoz;
        char ch, sir[200];
        struct sockaddr_in server;

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
        cmd=htons(cmd);
        send(sockfd,&cmd,sizeof(cmd),0);

        printf("Dati sirul de caractere: ");gets(sir);
        printf("Dati un caracter: ");scanf("%c",&ch);
        send(sockfd,&ch,sizeof(ch),0);
        send(sockfd,sir,sizeof(sir),0);

        printf("Pozitiile pe care apare caracterul:\n");
        recv(sockfd,&npoz,sizeof(npoz),0);
        poz=ntohs(npoz);
        while(poz!=201){
                printf("%hu ",poz);
                recv(sockfd,&npoz,sizeof(npoz),0);
                poz=ntohs(npoz);
        }
        printf("\n");
        close(sockfd);
}
