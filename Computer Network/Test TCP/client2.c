/*
Se da un sir de caractere si un caracter.
Se cere sa se elimine din sir prima aparitie a caracterului.
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
        char ch, str[100];
        int i=0;
        printf("Caracterul: ");scanf(" %c",&ch);getchar();
        printf("Sirul de caractere: ");gets(str);
        printf("da\n");
        send(c,&ch,sizeof(ch),0);
        send(c,str,sizeof(char)*(strlen(str)+1),0);
        memset(str,0,sizeof(str));
        do{
                recv(c,&ch,sizeof(char),0);
                str[i++]=ch;
        }while(ch!=0);
        printf("Noul sir: %s\n",str);

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
