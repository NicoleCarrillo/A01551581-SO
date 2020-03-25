#include <stdio.h>
#include <unistd.h>

int main() {
    int pid;
    pid = fork();
    if (pid != 0)
    {
        printf("Soy el proceso padre.\n");
    }
    else
    {
        printf("HIJO: Soy el proceso hijo.");
        execlp("ls","ls","-a","/",(char*)NULL);
    }
}