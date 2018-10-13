/*
Un client trimite unui server un sir de caractere.
Serverul va returna clientului numarul de caractere spatiu din sir.
*/

#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>

#define MYPORT 1234

int main(){
        int sockfd,newfd;
        struct sockaddr_in server,client;
        int l;

        sockfd=socket(AF_INET,SOCK_STREAM,0);
        if(sockfd==-1){
                printf("Eroare la crearea socketului server\n");
                return 1;
        }

        memset(&server,0,sizeof(server));
        server.sin_port=htons(MYPORT);
        server.sin_family=AF_INET;
        server.sin_addr.s_addr=INADDR_ANY;

        if(bind(sockfd,(struct sockaddr*)&server,sizeof(server))<0){
                printf("Eroare la bind\n");
                return 1;
        }

        listen(sockfd,5);
        l=sizeof(client);
        memset(&client,0,sizeof(client));
        while(1){
                uint16_t nr=0;
                char sir[200];
                int i;
                newfd=accept(sockfd,(struct sockaddr*)&client,&l);
                printf("S-a conectat un client\n");
                //Prelucrare date
                recv(newfd,sir,sizeof(sir),MSG_WAITALL);
                for(i=0;i<strlen(sir);i++)
                        if(sir[i]==' ')
                                nr++;
                nr=htons(nr);
                send(newfd,&nr,sizeof(nr),0);
                close(newfd);
        }
}
