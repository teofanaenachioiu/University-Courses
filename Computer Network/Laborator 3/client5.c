
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

#define SERVERPORT 1234

int main(){
        int sockfd,cmd=5;
        uint16_t n,nrd,dn,i,d;
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

        printf("Dati un numar: ");scanf("%hu",&n);
        n=htons(n);
        send(sockfd,&n,sizeof(n),0);

        printf("Sirul divizorilor:\n");
        recv(sockfd,&nrd,sizeof(nrd),0);
        d=ntohs(nrd);
        while(d!=0){
                printf("%hu ",d);
                recv(sockfd,&nrd,sizeof(dn),0);
                d=ntohs(nrd);
        }
        printf("\n");
        close(sockfd);
}
