/*
Un client trimite unui server un sir de caractere.
Serverul va returna clientului acest sir oglindit (caracterele sirului in ordine inversa).
*/

#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>

#define MYPORT 1234

char *strrev(char *str){
    int i = strlen(str) - 1, j = 0;
    char ch;
    while (i > j){
        ch = str[i];
        str[i] = str[j];
        str[j] = ch;
        i--;
        j++;
    }
    return str;
}

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
                char sir[200];
                newfd=accept(sockfd,(struct sockaddr*)&client,&l);
                printf("S-a conectat un client\n");
                //Prelucrare date
                recv(newfd,sir,sizeof(sir),MSG_WAITALL);
                strrev(sir);
                send(newfd,sir,sizeof(sir),0);
                close(newfd);
        }
}
