/*
Un client trimite unui server doua siruri de numere.
Serverul va returna clientului sirul de numere
care se regaseste in cel de-al doilea sir dar nu se regasesc in primul.
*/

#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <arpa/inet.h>
#include <string.h>
#include <unistd.h>

#define SERVERPORT 1234

void deservire_client(int c){
        uint16_t i,n1,n2,el,n;
        printf("n1=");scanf("%hu",&n1);
        n=htons(n1);
        send(c,&n,sizeof(n),0);
        for(i=0;i<n1;i++){
                printf("el[%hu]=",i);scanf("%hu",&el);
                el=htons(el);
                send(c,&el,sizeof(el),0);
        }
        printf("n2=");scanf("%hu",&n2);
        n=htons(n2);
        send(c,&n,sizeof(n),0);
        for(i=0;i<n2;i++){
                printf("el[%hu]=",i);scanf("%hu",&el);
                el=htons(el);
                send(c,&el,sizeof(el),0);
        }
        recv(c,&n,sizeof(n),0);
        n=ntohs(n);
        printf("Elementele: ");
        for(i=0;i<n;i++){
                recv(c,&el,sizeof(el),0);
                el=ntohs(el);
                printf("%hu ",el);
        }
        printf("\n");
}

int main(){
        int c;
        struct sockaddr_in server;

        c=socket(AF_INET,SOCK_STREAM,0);
        if(c<0){
                printf("Eroare la crearea socketului\n");
                return 1;
        }

        memset(&server,0,sizeof(server));
        server.sin_port=htons(SERVERPORT);
        server.sin_family=AF_INET;
        server.sin_addr.s_addr=inet_addr("127.0.0.1");

        if(connect(c,(struct sockaddr *)&server,sizeof(server))<0){
                printf("Eroare la conectarea la server\n");
                return 1;
        }

        deservire_client(c);

        close(c);
}
