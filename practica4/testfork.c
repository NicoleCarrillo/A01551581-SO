#include <stdio.h>
#include <unistd.h>

int main() {
    int pid= fork();

    if (pid!=0) 
    {
        printf("SOY EL PADRE \n");
    }
    else 
    {
        printf("SOY EL HIJO \n");
    }
}