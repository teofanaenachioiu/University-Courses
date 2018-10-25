/*
Se da un numar. Se cere sa se afiseze toate numerele prime mai mici ca numarul dat
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
        uint16_t n,x;
        printf("n= ");scanf("%hu",&n);
        n=htons(n);
        send(c,&n,sizeof(n),0);
        do{
                recv(c,&x,sizeof(x),0);
                x=ntohs(x);
                if(x!=0) printf("%hu ",x);
        }while(x!=0);
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
