/*
A client server implementation in C for the number guess problem.
The server chooses a random number.
The clients connect and send numbers to server.
The server returns to each client a status message:
– send a larger number
– send a lower number
– you guessed my number
– another client guessed the number. You are a looser !
*/
#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>
#include <time.h>
#include <stdlib.h>

int main() {
  int c;
  struct sockaddr_in server;
  uint16_t a, ok,toSend,toRecv,check;
  int min=-1,max=100;

  c = socket(AF_INET, SOCK_STREAM, 0);
  if (c < 0) {
    printf("Eroare la crearea socketului client\n");
    return 1;
  }

  memset(&server, 0, sizeof(server));
  server.sin_port = htons(1234);
  server.sin_family = AF_INET;
  server.sin_addr.s_addr = inet_addr("127.0.0.1");

  if (connect(c, (struct sockaddr *) &server, sizeof(server)) < 0) {
    printf("Eroare la conectarea la server\n");
    return 1;
  }

  srand(time(NULL));
  //verific statusul programului
  recv(c,&check,sizeof(check),0);
  check=ntohs(check);
  if(check==1){printf("Numarul a fost deja ghicit!\n");}
  else{
    while(1){
      while(1){
        //generare numar pe un anumit interval
        a=rand()%100;
        if(min<a && a<max) break;
      }
    printf("Numarul trimis: %hu\n",a);
    toSend = htons(a);
    send(c, &toSend, sizeof(toSend), 0);
    recv(c, &toRecv, sizeof(toRecv), MSG_WAITALL);
    ok = ntohs(toRecv);
    sleep(1);
    if(ok==0) {printf("Ai ghicit!\n");break;}
    else if(ok==1) {printf("Trebuie un numar mai mic!!!\n");max=a;}
          else if(ok==3) {printf("Trebuie un numar mai mare!!!\n");min=a;}
               else {printf("Ai pierdut!\n");break;}
    }
  }
  close(c);
}
