/*
Schimb de mesaje intre client si server (Obtinerea problemei de la test)
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

int main(){
        int c;
        struct sockaddr_in server;
        char ch, nume[30],msg[100];

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

        printf("Numele: ");gets(nume);
        send(c,nume, sizeof(char)*(strlen(nume)+1),0);
        int i=0;
        do{
                recv(c,&ch,sizeof(char),MSG_WAITALL);
                msg[i++]=ch;
        }while(ch!=0);
        printf("Profu: %s\n",msg);

        close(c);
}
