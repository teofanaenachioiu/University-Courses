/*
Un client trimite unui server doua siruri de caractere.
Serverul ii raspunde clientului cu caracterul care se regaseste
de cele mai multe ori pe pozitii identice in cele doua siruri
si cu numarul de aparitii ale acestui caracter.
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
        char sir1[100],sir2[100],ch;
        printf("Sir1: ");gets(sir1);
        printf("Sir2: ");gets(sir2);
        send(c,sir1,sizeof(char)*(strlen(sir1)+1),0);
        printf("Am trimis %s\n",sir1);
        send(c,sir2,sizeof(char)*(strlen(sir2)+1),0);
        printf("Am trimis %s\n",sir2);
        recv(c,&ch,sizeof(ch),0);
        printf("Caracterul este: %c\n",ch);
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
