/*****************************
* Server concurent cu fork() *
******************************/

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <errno.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <sys/wait.h>
#include <signal.h>

#define MYPORT 3490 //the port users will be connecting to
#define BACKLOG 10 //how many pending connections queue will hold

void sigchld_handler(int s){
        //sa-mi explice cineva la ce e buna functiaaaaaaaaa
        while(wait(NULL)>0);
}

int main(void){
        int sockfd, new_fd;//listen on sockfd, new connection on new_fd
        struct sockaddr_in my_addr;//my address information
        struct sockaddr_in their_addr; //connector's address information
        int sin_size;//dimensunea sockaddr_in; nu e necesara variabila
        struct sigaction sa;//si pe asta sa mi-o explice cinevaaaaa
        int yes=1;

        if((sockfd=socket(AF_INET,SOCK_STREAM,0))==-1){
                //create socket,validation
                perror("socket");
                exit(1);
        }

        if(setsockopt(sockfd,SOL_SOCKET,SO_REUSEADDR,&yes,sizeof(int))==-1){
                //Options for socket
                //SO_REUSEADDR allow reuse of local addresses, if this is supported by the protocol
                perror("setsockopt");
                exit(1);
        }
        my_addr.sin_family=AF_INET;//host byte order
        my_addr.sin_port=htons(MYPORT);//short, network byte order
        my_addr.sin_addr.s_addr=INADDR_ANY;//automatically fill with my IP
        memset(&(my_addr.sin_zero),'\0',8);

        if(bind(sockfd,(struct sockaddr *)&my_addr,sizeof(struct sockaddr))==-1){
                //set port to socket
                perror("bind");
                exit(1);
        }

        if(listen(sockfd,BACKLOG)==-1){
                //asteapta clienti
                perror("listen");
                exit(1);
        }

        sa.sa_handler=sigchld_handler; //reap all dead processes
        sigemptyset(&sa.sa_mask);// additional et of signals to be blocked
        sa.sa_flags=SA_RESTART;//
        if(sigaction(SIGCHLD, &sa, NULL)==-1){
                //acociaza semnalul cu functia
                perror("sigaction");
                exit(1);
        }

        while(1){
                //main accept() loop
                sin_size=sizeof(struct sockaddr_in);//nu e necesar sa o pun separat
                if((new_fd=accept(sockfd,(struct sockaddr *)&their_addr, &sin_size))==-1){
                        //cu noul descriptor trimit datele
                        perror("accept");
                        continue;
                }
                printf("Server: got connection from %s\n",inet_ntoa(their_addr.sin_addr));

                if(!fork()){
                        //child process
                        close(socket);//child doesn't neet the listener
                        if(send(new_fd,"Hello world\n",14,0)==-1)
                                perror("send");
                        close(new_fd);
                        exit(0);
                }
                close(new_fd);//parent doesn't need this
        }
        return 0;
}
