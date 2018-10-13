/*
Un client trimite unui server un sir de caractere si doua numere (fie acestea s, i, l).
Serverul va returna clientului subsirul de lungime l a lui s care incepe la pozitia i.
*/
#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>
#include <stdlib.h>
#include <pthread.h>
#define MYPORT 1234
#define NRL 5

void * deservire_client(void * f){
        int fd=(int)f;
        uint16_t l,i,poz,ind=0;
        char sir[100],sirnou[100];
//      recv(fd,sir,sizeof(sir),MSG_WAITALL);//De ce cand primesc prima data sirul nu mergeeeeee?
        recv(fd, &l, sizeof(l), MSG_WAITALL);
        recv(fd,&i,sizeof(i),MSG_WAITALL);
        l=ntohs(l);
        i=ntohs(i);
        recv(fd,sir,sizeof(sir),MSG_WAITALL);
//      printf("Datele: %s || %hu || %hu\n",sir,l,i);
        for(poz=i;poz<i+l;poz++){
                sirnou[ind]=sir[poz];
                ind++;
        }
        printf("Sir nou: %s\n",sirnou);
        send(fd,sirnou,sizeof(sirnou),0);
        close(fd);
        return NULL;
}


int main(){
        int sockfd, newfd;
        struct sockaddr_in server, client;

        sockfd=socket(AF_INET, SOCK_STREAM,0);
        if(sockfd==-1){
                printf("Eroare la crearea socketului server\n");
                exit(1);
        }

        memset(&server,0,sizeof(server));
        server.sin_port=htons(MYPORT);
        server.sin_family=AF_INET;
        server.sin_addr.s_addr=INADDR_ANY;

        if(bind(sockfd,(struct sockaddr *)&server, sizeof(server))==-1){
                printf("Eroare la bind\n");
                exit(1);
        }

        listen(sockfd,NRL);
        int l=sizeof(client);
        memset(&client,0,sizeof(client));

        while(1){
                pthread_t th;
                uint16_t ind=0,nr,suma=0;
                newfd=accept(sockfd,(struct sockaddr *)&client,&l);
                if(newfd==-1) printf("Eroare la accept\n");
                printf("S-a conectat un client\n");
                pthread_create(&th,NULL,&deservire_client,(void*)newfd);
                //pthread_join(th,NULL);
        }
}
