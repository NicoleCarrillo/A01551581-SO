#include <stdlib.h>
#include <stdio.h>
#include <fcntl.h>
#include <sys/types.h>
#include <sys/uio.h>
#include <unistd.h>

int main(int argn, char **argv){
    char *fileName = argv[1];
    char buffer;
    char espacio = 0;
    unsigned int numChars = 0, numLines = 0, numWords = 0;
    int fd = open(fileName, O_RDONLY);
    if(fd < 0) {
        printf("No se puede abrir \n");
        exit(1);
    }
    while(read(fd, &buffer, 1)){
        numChars += 1;
        if(buffer =='\n') numLines += 1;
        if((buffer ==' ' || buffer == '\n'|| buffer == EOF)&& espacio == 0){
            espacio = 1;
            numWords += 1;
        }else if(buffer != ' ' && buffer != '\n' && buffer!=EOF){
            espacio = 0;
        }
    }
    printf("%d %d %d %s\n",numLines,numChars,numWords,fileName);
    close(fd);
    exit(0);
}