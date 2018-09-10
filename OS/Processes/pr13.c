#include<unistd.h>
#include<stdio.h>
#include<sys/types.h>
#include<sys/wait.h>
#include<fcntl.h>
#include<string.h>
int main(){
  int f2g,g2f;
  char str[30];
  strcpy(str,"Abracadabra");
  f2g=open("fisf2g",O_WRONLY);
  g2f=open("fisg2f",O_RDONLY);
  write(f2g,str,strlen(str)*sizeof(char));
  while(strlen(str)>0){
    read(g2f,str,strlen(str)*sizeof(char));
    printf("Din f: %s\n",str);
    strcpy(str,str+1);
    write(f2g,str,strlen(str)*sizeof(char));
  }
  close(f2g);close(g2f);
  return 0;
  }
