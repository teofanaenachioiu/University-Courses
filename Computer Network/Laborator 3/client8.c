
/*
Un client trimite unui server doua siruri de numere.
Serverul va returna clientului sirul de numere comune celor doua siruri primite.
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
        uint16_t ind,n1,n2,cmd=8,n;

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

        cmd=htons(cmd);
        send(sockfd,&cmd,sizeof(cmd),0);

        //Numarul de elemente array 1
        printf("n1="); scanf("%hu",&n1);
        uint16_t elem1[n1];
        n=htons(n1);
        send(sockfd,&n,sizeof(n),0);

        //Elementele array-ului
        printf("Dati numerele primului sir\n");
        ind=0;
        while(ind<n1){
                printf("NR%d: ",ind+1);scanf("%hu",&elem1[ind]);
                elem1[ind]=htons(elem1[ind]);
                send(sockfd,&elem1[ind],sizeof(elem1[ind]),0);
                ind=ind+1;
        }

        //Sirul 2
        printf("n2=");scanf("%hu",&n2);
        uint16_t elem2[n2];
        n=htons(n2);
        send(sockfd,&n,sizeof(n),0);

        printf("Dati numerele celui de-al doilea sir\n");
        ind=0;
        while(ind<n2){
                printf("NR%d: ",ind+1);scanf("%hu",&elem2[ind]);
                elem2[ind]=htons(elem2[ind]);
                send(sockfd,&elem2[ind],sizeof(elem2[ind]),0);
                ind=ind+1;
        }

        //Numerele comune
        recv(sockfd,&n,sizeof(n),0);
        n=ntohs(n);
        printf("Numerele sunt:\n");
        uint16_t x,nx;
        for(ind=0;ind<n;ind++){
                recv(sockfd,&nx,sizeof(nx),MSG_WAITALL);
                x=ntohs(nx);
                printf("%hu \n",x);

         }
        close(sockfd);
}
