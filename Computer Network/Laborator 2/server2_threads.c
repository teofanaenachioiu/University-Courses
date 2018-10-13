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
#include <stdlib.h>
#include <pthread.h>

#define MYPORT 1235

/*int freeThreads[10];
typedef struct PARAM{
        int fd;
        int index;
}_PARAM;*/

void * deservire_client(void *f){
        int fd=(int)f;
        char sir[200];
        uint16_t nr=0;
        recv(fd,sir,sizeof(sir),MSG_WAITALL);
        for(int i=0;i<strlen(sir);i++){
                if(sir[i]==' ') nr++;
        }
        nr=htons(nr);
        send(fd,&nr,sizeof(nr),0);
        //printf("Am intrat\n");
        close(fd);
        return NULL;
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
                int i=0;
                pthread_t th[10];

                newfd=accept(sockfd,(struct sockaddr*)&client,&l);
                if(newfd==-1)printf("Eroare la accept.\n");
                else{
                        printf("S-a conectat un client\n");

                        pthread_create(&th[i], NULL,&deservire_client,(void *) newfd);
                        i++;
                        }


        }
}