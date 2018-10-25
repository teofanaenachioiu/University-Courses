/*
Se da un numar. Se cere sa se afiseze toate numerele prime mai mici ca numarul dat
*/

#include <stdio.h>
#include <stdlib.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <string.h>
#include <arpa/inet.h>
#include <fcntl.h>
#include <unistd.h>
#include <pthread.h>

#define MYPORT 1234

uint16_t ePrim(uint16_t nr){
        uint16_t ok=1,d;
        for(d=2;d<=nr/2 && ok==1;d++)
                if(nr%d==0)
                        ok=0;
        return ok;
}

void *deservire_client(void * arg){
        int sockfd=*((int*)arg);
        uint16_t i,n,x;
        recv(sockfd,&n,sizeof(n),0);
        n=ntohs(n);
        for(i=2;i<=n;i++)
                if(ePrim(i)==1){
                        x=htons(i);
                        send(sockfd,&x,sizeof(x),0);
                }
        x=htons(0);
        send(sockfd,&x,sizeof(x),0);
        close(sockfd);
        pthread_exit(NULL);
}

int main(){
        int s;
        struct sockaddr_in server, client;
        int c,l;

        s=socket(AF_INET,SOCK_STREAM,0);
        if (s < 0) {
                printf("Eroare la crearea socketului server\n");
                return 1;
        }

        memset(&server, 0, sizeof(server));
        server.sin_port = htons(MYPORT);
        server.sin_family = AF_INET;
        server.sin_addr.s_addr = INADDR_ANY;

        if (bind(s, (struct sockaddr *) &server, sizeof(server)) < 0) {
                printf("Eroare la bind\n");
                return 1;
        }

        listen(s, 5);

        l = sizeof(client);
        memset(&client, 0, sizeof(client));

        pthread_t tid[30];
        int i=0;
        while(1){
                c=accept(s,(struct sockaddr *)&client,&l);
                printf("S-a conectat un client.\n");

                if( pthread_create(&tid[i++], NULL, deservire_client, &c) != 0 ) {
                        printf("Failed to create thread\n");
                }

                if( i >= 30) {
                        i = 0;
                        while(i < 30) {
                                pthread_join(tid[i++],NULL);
                        }
                i = 0;
                }
        }
}
