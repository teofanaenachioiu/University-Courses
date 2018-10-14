/*
Un client trimite unui server un sir de caractere.
Serverul va returna clientului acest sir oglindit (caracterele sirului in ordine inversa).
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
        int sockfd,cmd=3;
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
        printf("Dati sirul de caractere: ");
        gets(sir);
        send(sockfd,sir,sizeof(sir),0);
        recv(sockfd,sir,sizeof(sir),0);
        printf("Oglinditul sirului: %s\n",sir);
        close(sockfd);
}