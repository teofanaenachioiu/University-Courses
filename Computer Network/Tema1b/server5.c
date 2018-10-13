/*
Un client trimite unui server un numar.
Serverul va returna clientului sirul divizorilor acestui numar.
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

void deservire_client(int fd){
        uint16_t divizor,d=2,nr;
        recv(fd, &nr, sizeof(nr), MSG_WAITALL);
        nr=ntohs(nr);
        //printf("Numarul %hu are divizorii: ",nr);
        for(d=2;d<=nr/2;d++){
                if(nr%d==0){
                        //printf("%hu ",d);
                        divizor = htons(d);
                        send(fd, &divizor, sizeof(divizor), 0);
                }
        }
        //printf("\n");
        d=0;
        divizor=htons(d);
        send(fd,&divizor,sizeof(divizor),0);
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
                uint16_t ind=0,nr,suma=0;
                newfd=accept(sockfd,(struct sockaddr *)&client,&l);
                printf("S-a conectat un client\n");
                if(fork()==0){
                        deservire_client(newfd);
                        return 0;
                }
        }
}
