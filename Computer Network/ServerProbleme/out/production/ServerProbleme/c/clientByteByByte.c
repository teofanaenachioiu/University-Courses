#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>
#include <unistd.h>
#include <arpa/inet.h>

int main() {
	int c;
	struct sockaddr_in server;
	char msg[256];

	char ccc;

	c = socket(AF_INET, SOCK_STREAM, 0);

	if (c < 0) {
		printf("Eroare la crearea socketului...\n");;
		return 1;
	}

	memset(&server, 0, sizeof(server));
	server.sin_port = htons(54321);
	server.sin_family = AF_INET;
	server.sin_addr.s_addr = inet_addr("172.30.117.98");

	if (connect(c, (struct sockaddr *) &server, sizeof(server)) < 0 ) {
		printf("Eroare la conectare...\n");
		return 1;
	}

    while (1) {

        char backMsg[256];
        bzero(backMsg, 256);

        char b;

        int cod = recv(c, &b, 1, 0);

        int i = 0;

        while (b != '\n') {
            backMsg[i] = b;
            cod = recv(c, &b, 1, 0);
            i++;
        }

        printf("Raspuns: %s\n", backMsg);

        printf("Mesaj: ");
        bzero(msg, 256);

        fgets(msg, sizeof(msg), stdin);

        for (int j = 0; j < strlen(msg); j++) {
            send(c, &msg[j], 1, 0);
        }

        printf("Sent: %s\n", msg);
	}
	close(c);
}