
//Server concurent cu fork-uri

#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>
#include <stdlib.h>
#include <pthread.h>

#define MYPORT 1234
#define NRT 10

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

void * deservire3(int newfd){
        char sir[200];
        recv(newfd,sir,sizeof(sir),MSG_WAITALL);
        strrev(sir);
        send(newfd,sir,sizeof(sir),0);
        close(newfd);
        return NULL;
}

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

void * deservire4(int newfd){
        char sir[200],sir1[100],sir2[100];
        recv(newfd,sir1,sizeof(sir1),MSG_WAITALL);
        recv(newfd,sir2,sizeof(sir2),MSG_WAITALL);
        interclasare(sir,sir1,sir2);
        send(newfd,sir,sizeof(sir),0);
        close(newfd);
        return NULL;
}

void * deservire6(int newfd){
        uint16_t poz,i;
        char sir[200],ch;
        recv(newfd,&ch,sizeof(ch),MSG_WAITALL);
        recv(newfd,sir,sizeof(sir),MSG_WAITALL);
        for(i=0;i<strlen(sir);i++){
                if(sir[i]==ch){
                        poz=htons(i);
                        send(newfd,&poz,sizeof(poz),0);
                }
        }
        i=201;
        poz=htons(i);
        send(newfd,&poz,sizeof(poz),0);
        close(newfd);
        return NULL;
}

void * deservire5(int newfd){
        uint16_t n,d=1,nrd;
        recv(newfd,&n,sizeof(n),0);
        n=ntohs(n);
        for(d=1;d<=n/2;d++){
                if(n%d==0){
                        nrd=htons(d);
                        send(newfd,&nrd,sizeof(nrd),0);
                }
        }
        d=0;
        nrd=htons(d);
        send(newfd,&nrd,sizeof(nrd),0);
        close(newfd);
        return NULL;
}

void * deservire7(int newfd){
        uint16_t l,i,poz,ind=0;
        char sir[100],sirnou[100];
//   recv(fd,sir,sizeof(sir),MSG_WAITALL);//De ce cand primesc prima data sirul nu mergeeeeee?
        recv(newfd, &l, sizeof(l), MSG_WAITALL);
        recv(newfd,&i,sizeof(i),MSG_WAITALL);
        l=ntohs(l);
        i=ntohs(i);
        recv(newfd,sir,sizeof(sir),MSG_WAITALL);
        for(poz=i;poz<i+l;poz++){
                sirnou[ind]=sir[poz];
                ind++;
        }

        send(newfd,sirnou,sizeof(sirnou),0);
        close(newfd);
        return NULL;
}

int apare(uint16_t e, uint16_t elem[100],uint16_t n){
        uint16_t i=0;
        while(i<n){
                if(elem[i]==e) return 1;
                i++;
        }
        return 0;
}

void * deservire8(int newfd){
        uint16_t n1,n2,i;
        recv(newfd,&n1,sizeof(n1),0);
        n1=ntohs(n1);
        uint16_t elem1[100];
        for(i=0;i<n1;i++){
                recv(newfd,&elem1[i],sizeof(elem1[i]),0);
                elem1[i]=ntohs(elem1[i]);
        }
        recv(newfd,&n2,sizeof(n2),0);
        n2=ntohs(n2);

        uint16_t elem2[100],x,nr=0;
        for(i=0;i<n2;i++){
                recv(newfd,&x,sizeof(x),0);
                x=ntohs(x);
                if(apare(x,elem1,n1)==1) {
                        elem2[nr]=x;nr++;
                }
        }

        //Transmiterea datelor
        uint16_t n;
        n=htons(nr);
        send(newfd,&n,sizeof(n),0);

        for(i=0;i<nr;i++){
                n=htons(elem2[i]);
                send(newfd,&n,sizeof(n),0);
        }
        close(newfd);
        return NULL;
}

int main(){
        int sockfd,newfd;
        struct sockaddr_in server,client;
        uint16_t comanda[NRT];

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
        int l=sizeof(client);
        memset(&client,0,sizeof(client));
        while(1){
                int i=0;
                newfd=accept(sockfd,(struct sockaddr*)&client,&l);
                if(newfd==-1)printf("Eroare la accept.\n");
                else{
                        printf("S-a conectat un client\n");
                        recv(newfd,&comanda[i],sizeof(comanda),0);
                        comanda[i]=ntohs(comanda[i]);
                        i++;
                        if(fork()==0){
                                if(comanda[i-1]==3) deservire3(newfd);
                                if(comanda[i-1]==4) deservire4(newfd);
                                if(comanda[i-1]==5) deservire5(newfd);
                                if(comanda[i-1]==6) deservire6(newfd);
                                if(comanda[i-1]==7) deservire7(newfd);
                                if(comanda[i-1]==8) deservire8(newfd);
                        }
                    }
        }
}
