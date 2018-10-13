/*
Un client trimite unui server un sir de caractere si un caracter.
Serverul va returna clientului toate pozitiile
pe care caracterul primit se regaseste in sir.
*/

#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>
#include <stdlib.h>

#define MYPORT 1235
#define NRL 5

void deservire_client(int fd){
        uint16_t poz,i;
        char sir[200],ch;
        recv(fd,&ch,sizeof(ch),MSG_WAITALL);
        recv(fd,sir,sizeof(sir),MSG_WAITALL);
//      printf("%s || %c\n",sir,ch);
//      printf("%d\n",strlen(sir));
        for(i=0;i<strlen(sir);i++)
                if(sir[i]==ch){
//                      poz=i;
//                      printf("%hu\n",poz);
                        poz=htons(i);
//                      send(fd,&poz,sizeof(poz),0);
                }
        poz=htons(-1);
//      send(fd,&poz,sizeof(poz),0);
        close(fd);
}

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
                newfd=accept(sockfd,(struct sockaddr *)&client,&l);
                printf("S-a conectat un client\n");
                if(fork()==0){
                        deservire_client(newfd);
                        return 0;
                }
        }
}
