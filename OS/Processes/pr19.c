#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <sys/types.h>
int main(int argc, char* argv[])
{
    int pd[2];                                          // pipe descriptors

    int res = pipe(pd);
    if (res == -1)                                      // fail to create pipe
    {
        perror("pipe() error: ");
        exit(EXIT_FAILURE);
    }

    int pid = fork();
    if (pid == -1)                                      // fail to create child
    {
        perror("fork() error: ");
        exit(EXIT_FAILURE);
    }

    if (pid == 0)                                       // in child process
    {
        //close(pd[1]);                         // close the write descriptor

        int n,d,copie;
        int go = 1;
        while (go)
        {
                read(pd[0], &n, sizeof(int));
                if(n!=-1){
                        d=2;
                        copie=n;
                        while(d<=n/2)
                        {
                                if(copie%d==0) break;
                                else d++;
                        }
                        if(d==n/2+1) printf("Nr prim: %d\n",copie);
                }
   else go=0;

        }
        close(pd[1]);
        close(pd[0]);
        exit(0);
    }

        close(pd[0]);                   // close the read descriptor

        int n;
        int go = 1;
        while (go)
        {
                printf("n: ");
                scanf("%d", &n);
                write(pd[1],&n,sizeof(int));
                if (n == -1 )
                {
                        go = 0;
                }
                //sleep(1);
        }
        wait(0);

    close(pd[1]);
    return 0;
}
