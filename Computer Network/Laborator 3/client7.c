/*
Un client trimite unui server un sir de caractere si doua numere (fie acestea s, i, l).
Serverul va returna clientului subsirul de lungime l a lui s care incepe la pozitia i.
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
        uint16_t l,i,cmd=7;
        struct sockaddr_in server;
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
        cmd=htons(cmd);
        send(sockfd,&cmd,sizeof(cmd),0);

        printf("Dati sirul de caractere: ");scanf(" %[^\n]s", sir);
        printf("Dati lungimea subsirului: ");scanf("%hu",&l);
        printf("Dati pozitia de start a subsirului: ");scanf("%hu",&i);

        l=htons(l);
        i=htons(i);

        send(sockfd,&l,sizeof(l),0);
        send(sockfd,&i,sizeof(i),0);
        send(sockfd,sir,sizeof(sir),0);

        recv(sockfd,sir,sizeof(sir),0);
        printf("Subsirul: %s\n",sir);

        close(sockfd);
}
