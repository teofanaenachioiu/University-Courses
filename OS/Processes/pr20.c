#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

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
        close(pd[1]);                           // close the write descriptor

        int n;
        int go = 1;
        while (go)
        {
                read(pd[0], &n, sizeof(int));   // read

                if (n == 4)
                {
                        go = 0;
                }

                printf("[In CHILD] n: %d\n", n);
        }

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

                if (n == 4)
                {
                        go = 0;
                }

                write(pd[1], &n, sizeof(int));  // write

        //      printf("[In PARENT] n: %d\n", n);
        //      sleep(2);
        }

    int status;
 //   wait(&status);
  //  printf("\n[In PARENT] Child has finished with exit status: %d\n", status);

    close(pd[1]);wait(0);
    return 0;
}
                     