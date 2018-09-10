/*
Exemplu utilizare exec
*/

#include<stdio.h>
#include<unistd.h>
int main(){
        printf("inainte\n");
        execlp("echo","echo","aici",NULL);
        printf("dupa\n");
        return 0;
}


