#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>
#include <unistd.h>
#include <stdlib.h>


void newClient(char msg[100], struct sockaddr_in server, struct sockaddr_in client, int newPort) {
	printf("Am primit mesajul %s", msg);

	int s1;

	s1 = socket(AF_INET, SOCK_DGRAM, 0);
        if (s1 < 0) {
                printf("Eroare la crearea socketului...\n");
                exit(1);
        }

	server.sin_port = htons(newPort);


	if (bind(s1, (struct sockaddr *) &server, sizeof(server)) < 0) {
                printf("Eroare la bind...\n");
                exit(1);
        }

        char backMsg[100];
 	    printf("Dati mesajul: ");
        fgets(backMsg,  sizeof(backMsg), stdin);
        printf("Trimit catre server stringul: %s\n", backMsg);
        sendto(s1, backMsg, sizeof(backMsg), 0, (struct sockaddr *) &client, (socklen_t) sizeof(client));
        printf("Am trimis si astept mesjul doi de la client!\n");

	int clientSize = sizeof(client);
	char m1[100];

	recvfrom(s1, m1, sizeof(m1), 0, (struct sockaddr *) &client, &clientSize);
	printf("Am primit mesajul doi %s", m1);


	printf("Dati mesajul: ");
        fgets(backMsg,  sizeof(backMsg), stdin);
        printf("Trimit catre server stringul: %s\n", backMsg);
        int cc = sendto(s1, backMsg, sizeof(backMsg), 0, (struct sockaddr *) &client, (socklen_t) sizeof(client));
        printf("Am trimis %d octeti!\n", cc);



	close(s1);
	exit(0);
}

int main() {
	int s;
	struct sockaddr_in server;
	int c, l;

	s = socket(AF_INET, SOCK_DGRAM, 0);
	if (s < 0) {
		printf("Eroare la crearea socketului...\n");
		return 1;
	}

	memset(&server, 0, sizeof(server));
	server.sin_port = htons(1234);
	server.sin_family = AF_INET;
	server.sin_addr.s_addr = INADDR_ANY;

	if (bind(s, (struct sockaddr *) &server, sizeof(server)) < 0) {
		printf("Eroare la bind...\n");
		return 1;
	}

	int newPort = 3000;

	while(1) {
		char msg[100];
		printf("Astept clienti...\n");
		struct sockaddr_in client;
 		l = sizeof(client);
        	memset(&client, 0, sizeof(client));
		recvfrom(s, msg, sizeof(msg), 0, (struct sockaddr *) &client, &l);

		newPort++;

		if (fork() == 0) {
		printf("New client FOUND:\n");
			newClient(msg, server, client, newPort);
		}
	}

	close(s);
}
