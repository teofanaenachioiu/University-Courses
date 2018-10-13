/*
A client server implementation in C for the number guess problem.
The server chooses a random number.
The clients connect and send numbers to server.
The server returns to each client a status message:
- send a larger number
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
#include <pthread.h>
#include <time.h>
#include <stdlib.h>

int nr,ghicit;//nr=numarul de ghicit; ghicit=1(numarul a fost ghicit)/0(numarul nu a fost ghicit)
pthread_mutex_t mutex;

void *deservire_client(void * c) {
  int sock=(int)c;

  uint16_t nrc, ok, toSend, toRecv, check;
  //nrc=numarul primit de la client
  //ok=statusul numarului primit de la client
  //toSend='ok' convertit
  //toRecv='nrc' de convertit
  //check=statusul programului: numarul a fost sau nu ghicit(1/0)

  //transimit clientului statusul programului
  check=htons(ghicit);
  send(sock,&check,sizeof(check),0);

  //analizez statusul numarului
  while(1){
    if(ghicit==1) {
      //numarul a fost ghicit de alt client
      ok=2;
      }
    else{
      //numarul nu a fost inca ghicit
      //ok=0(am ghicit); ok=1(trebuie un numar mai mare); ok=3(trebuie un numar mai mic);
      recv(sock,&toRecv,sizeof(toRecv),MSG_WAITALL);
      nrc=ntohs(toRecv);
      printf("Nr de ghicit %hu, nr dat de client %hu\n",nr,nrc);
      if(nrc==nr) {
        ok=0;
        pthread_mutex_lock(&mutex);
        ghicit=1;
        pthread_mutex_unlock(&mutex);
      }
      else if(nrc>nr) ok=1;
           else if (nrc<nr)ok=3;
    }
    toSend=htons(ok);
    send(sock,&toSend,sizeof(toSend),0);
    if(ok==0 || ok==2) break;//numarul a fost ghicit(de mine sau de alt client)
  }
  close(sock);
  return NULL;
}

int main() {
  pthread_mutex_init(&mutex,NULL);
  int s;
  struct sockaddr_in server, client;
  int c[10], l;

  srand(time(NULL));
  nr=rand()%100;
  ghicit=0;

  s = socket(AF_INET, SOCK_STREAM, 0);
  if (s < 0) {
    printf("Eroare la crearea socketului server\n");
    return 1;
  }

  memset(&server, 0, sizeof(server));
  server.sin_port = htons(1234);
  server.sin_family = AF_INET;
  server.sin_addr.s_addr = INADDR_ANY;

  //reutilizez socket-ul
  int yes=1;
  if (setsockopt(s, SOL_SOCKET, SO_REUSEADDR, &yes, sizeof(yes)) == -1) {
    perror("setsockopt");
    exit(1);
  }

  if (bind(s, (struct sockaddr *) &server, sizeof(server)) < 0) {
    printf("Eroare la bind\n");
    return 1;
  }

  listen(s, 5);

  l = sizeof(client);
  memset(&client, 0, sizeof(client));

  int ind=0;
  pthread_t th[10];
  while (1) {
    c[ind] = accept(s, (struct sockaddr *) &client, &l);
    printf("S-a conectat un client.\n");

    pthread_create(&th[ind],NULL, &deservire_client,(void*)c[ind]);
    ind++;
  }
}