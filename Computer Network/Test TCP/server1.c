/*
Schimb de mesaje intre client si server (Obtinerea problemei de la test)
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
#include <string.h>

#define MYPORT 1234

void reverse(char s[]){
        int i, j;
        char c;
        for (i=0,j=strlen(s)-1; i<j; i++,j--) {
                c=s[i];
                s[i]=s[j];
                s[j]=c;
        }
}

//Implementarea functiei itoa
void itoa(int n, char s[]){
        int i, sign;

        if ((sign=n)<0)  /* record sign */
                n=-n;          /* make n positive */
                i=0;
        do{       /* generate digits in reverse order */
                s[i++]=n%10+'0';   /* get next digit */
        }while((n/=10)>0);     /* delete it */
        if(sign<0)
                s[i++]='-';
        s[i]='\0';
        reverse(s);
}

void * deservire_client(void *arg){
        char nume[30],msg[100], ch, nrStr[20];
        uint16_t i=0,nrp;
        int c=*((int*)arg);

        //Conectare client
        do{
                recv(c,&ch,sizeof(ch),MSG_WAITALL);
                nume[i++]=ch;
        }while(ch!=0);
        printf("S-a conectat %s.\n",nume);

        //Generare problema random
        do{
                nrp=rand()%11;
        }while(nrp==0);
        itoa(nrp,nrStr);

        //Compunere mesaj
        strcpy(msg,"Salut ");
        strcat(msg, nume);
        strcat(msg,"! Ai de rezolvat problema ");
        strcat(msg,nrStr);
        strcat(msg,". Succes!");
        printf("Trimit: %s\n",msg);
        send(c,msg,sizeof(char)*(strlen(msg)+1),0);

        close(c);
        pthread_exit(NULL);
}

int main(){
        int s;
        struct sockaddr_in server,client;
        int c, l;

        s=socket(AF_INET,SOCK_STREAM,0);
        if(s<0){
                printf("Eroare la crearea socketului server\n");
                return 1;
        }

        memset(&server,0,sizeof(server));
        server.sin_port=htons(MYPORT);
        server.sin_family=AF_INET;
        server.sin_addr.s_addr=INADDR_ANY;

        if(bind(s,(struct sockaddr*)&server,sizeof(server))<0){
                printf("Eroare la bind\n");
                return 1;
        }

        listen(s,5);

        l=sizeof(client);
        memset(&client,0,sizeof(client));

        pthread_t tid[30];
        int i=0;

        while(1){
                c=accept(s,(struct sockaddr *)&client,&l);
                if(pthread_create(&tid[i++],NULL,deservire_client,&c)!=0){
                        printf("Eroare la crearea thread-ului\n");
                }

                if(i>=30){
                        i=0;
                        while(i<30){
                                pthread_join(tid[i++],NULL);
                        }
                        i=0;
                }
        }
}
