/*
Un client trimite unui server doua siruri de caractere ordonate.
Serverul va interclasa cele doua siruri si va returna clientului sirul rezultat interclasat.
*/

#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>

#define MYPORT 1234

void interclasare(char* str,char *str1, char *str2){
        int i=0,j=0,k = 0;
        while(i<strlen(str1) && j<strlen(str2)){
                if(str1[i]<str2[j]){
                        str[k]=str1[i];
                        i++;
                        k++;
                }
                else {
                        str[k]=str2[j];
                        j++;
                        k++;
                }
        }
        if(i<strlen(str1)){
                str[k]=str1[i];
                i++;
                k++;
        }
        if(j<strlen(str2)){
                str[k]=str2[j];
                j++;
                k++;
        }
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
                char sir[200],sir1[100],sir2[100];
                newfd=accept(sockfd,(struct sockaddr*)&client,&l);
                printf("S-a conectat un client\n");
                //Prelucrare date
                recv(newfd,sir1,sizeof(sir1),MSG_WAITALL);
                recv(newfd,sir2,sizeof(sir2),MSG_WAITALL);
                interclasare(sir,sir1,sir2);
                send(newfd,sir,sizeof(sir),0);
                close(newfd);
        }
}
