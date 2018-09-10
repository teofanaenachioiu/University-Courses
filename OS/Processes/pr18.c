#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/wait.h>
int main(int argc, char* argv[])
{
    int pd[2];                                          // pipe descriptors
    int res = pipe(pd);
    if (res == -1)                                      // fail to create pipe
    {
        exit(0);
    }

    int pid = fork();
    if (pid == -1)                                      // fail to create child
    {
        exit(0);
    }

    if (pid == 0)                                       // in child process
    {
        close(pd[1]);                           // close the write descriptor
        int go = 1;
        char tmp1[50],tmp2[50];
        while (go)
        {
                read(pd[0], tmp1, sizeof(tmp1));        // read

                if (strcmp(tmp1, "STOP") != 0)
                {
                        read(pd[0],tmp2,sizeof(tmp2));
                        strcat(tmp1," ");
                        strcat(tmp1,tmp2);
                        printf("Sirul rezultat: %s\n",tmp1);
                }
                else go=0;
        }

        close(pd[0]);
        exit(0);
    }

        close(pd[0]);                   // close the read descriptor

        int go = 1;
        char sir1[50],sir2[50];
        while (go)
        {
                printf("Sir1: ");
                scanf("%s", sir1);
                write(pd[1], sir1, sizeof(sir1));
                if (strcmp(sir1, "STOP") != 0)
                {
                        printf("Sir2: ");
                        scanf("%s",sir2);
                        write(pd[1],sir2,sizeof(sir2));
                }
                else go=0;

                //write(pd[1], sir, sizeof(sir));       // write
                sleep(1);
        }
    close(pd[1]);
    return 0;
}
