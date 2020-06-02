#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/uio.h>
#include <unistd.h>

void writeToFile(int tmp, char *word, char *searchWord, char *replaceWord) {
  if (strncmp(word, searchWord, strlen(searchWord)) == 0) {
    write(tmp, replaceWord, strlen(replaceWord));
  } else {
    write(tmp, word, strlen(word));
  }
}
int main(int argn, char **argv) {
  char *fileName = argv[3];
  char *searchWord = argv[1];
  char *replaceWord = argv[2];
  char *tmpFileName = "/tmp/replace.txt";
  char word[100];
  int i = 0;
  char character;
  char espacio = 0;
  int fd = open(fileName, O_RDONLY);
  int tmp = open(tmpFileName, O_WRONLY | O_CREAT, 0600);
  if (fd < 0) {
    printf("No se puede abrir \n");
    exit(1);
  }
  while (read(fd, &character, 1)) {
    if ((character == ' ' || character == '\n') && espacio == 0) {
      espacio = 1;
      word[i++] = character;
      word[i] = '\0';
      i = 0;
      writeToFile(tmp, word, searchWord, replaceWord);

    } else if (character != ' ' && character != '\n') {
      espacio = 0;
      word[i++] = character;
    }
  }
  word[i] = '\0';
  writeToFile(tmp, word, searchWord, replaceWord);
  close(fd);
  //PARA RENOMBRAR
  char pathToTmp[] = "/tmp/";
  strcat(pathToTmp, fileName);
  unlink(fileName);
  link(tmpFileName, pathToTmp);
  unlink(tmpFileName);
  close(tmp);
  exit(0);
}