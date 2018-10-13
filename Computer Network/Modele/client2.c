/*****************************************
* client.c-- a stream socket client demo *
******************************************/

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <errno.h>
#include <string.h>
#include <netdb.h>
#include <sys/types.h>
#include <netinet/in.h>
#include <sys/socket.h>

#define PORT 3490 //portul la care se conecteaza clientul
#define MAXDATASIZE 100 //numarul maxim de bytes pe care putem sa-i primim

int main(int argc, char *argv[]){
        int sockfd, numbytes; //descriptorul pentru socket; numarul de bytes primiti
        char buf[MAXDATASIZE];//bufferul
        struct hostent *he;//tine de DNS, ca sa aflam adresa IP dupa numele host-ului
        struct sockaddr_in their_addr; //informatii despre host-ul server(cel la care ne conectam)

        if (argc != 2) {
                //verific numarul de argumente
                //numele host-ului server trebuie sa fie dat ca argument
            fprintf(stderr,"usage: client hostname\n");
            exit(1);
        }

        if ((he=gethostbyname(argv[1])) == NULL) {
                //obtinem info despre host-ul server(e dat numele ca parametru)
            perror("gethostbyname");
            exit(1);
        }

        if ((sockfd = socket(AF_INET, SOCK_STREAM, 0)) == -1) {
                //creez socketul
                //AF_INET se refera la protocolul de internet
                //SOCK_STREAM indica faptul ca se utilizeaza TCP
                //Se pune 0 aproape mereu, sa fie ales protocolul corect. Smth like this...
            perror("socket");
            exit(1);
        }

        //stabilim detaliile serverului la care se va conecta clientul
        their_addr.sin_family = AF_INET;    // host byte order
        their_addr.sin_port = htons(PORT);  // short, network byte order
        their_addr.sin_addr = *((struct in_addr *)he->h_addr);//serverul dat ca parametru
        memset(&(their_addr.sin_zero), '\0', 8);  // zero the rest of the struct

        if (connect(sockfd, (struct sockaddr *)&their_addr,
                                              sizeof(struct sockaddr)) == -1) {
                //conectam socketul cu serverul
            perror("connect");
            exit(1);
        }

        if ((numbytes=recv(sockfd, buf, MAXDATASIZE-1, 0)) == -1) {
                //primim dateleeeeee
            perror("recv");
            exit(1);
        }

        buf[numbytes] = '\0';//sa fie un array de char corect

        printf("Received: %s",buf);

        close(sockfd);

        return 0;
    }
