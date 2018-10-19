
#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>
#include <pthread.h>
int main() {
  int s;
  struct sockaddr_in server, client;
  int c, l;

  s = socket(AF_INET, SOCK_STREAM, 0);
  if (s < 0) {
    printf("Eroare la crearea socketului server\n");
    return 1;
  }

  memset(&server, 0, sizeof(server));
  server.sin_port = htons(1234);
  server.sin_family = AF_INET;
  server.sin_addr.s_addr = INADDR_ANY;

  if (bind(s, (struct sockaddr *) &server, sizeof(server)) < 0) {
     printf("Eroare la bind\n");
     return 1;
  }

     listen(s, 5);

     l = sizeof(client);
     memset(&client, 0, sizeof(client));

  while (1) {
    char msg[100];
    c = accept(s, (struct sockaddr *) &client, &l);

    char ch, mesaj[100];
    strcpy(mesaj,"da");
    while(strcmp(mesaj,"bye")){
         uint16_t i=0;
         do{
                 recv(c,&ch,sizeof(ch),0);
                 mesaj[i]=ch;
                 i++;
        }while(ch!=0);
        printf("Alinutza: %s\n",mesaj);
        printf("Teo: ");gets(msg);
        send(c,msg,sizeof(char)*(strlen(msg)+1),0);
        memset(mesaj,0,sizeof(mesaj));
        memset(msg,0,sizeof(msg));
    }
    close(c);
    // sfarsitul deservirii clientului;
  }
}
