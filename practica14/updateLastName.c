#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

typedef struct s{
    unsigned char id;
    unsigned char semester;
    char firstName[20];
    char lastName[20];
} Student;

int main(int argn, char **argv){
    int row = atoi(argv[1]);
    char newlast[20] = "";

    Student *student;
    student = (Student *)malloc(sizeof(Student));

    int src = open("class.bin", O_RDWR);
    pread(src, student, sizeof(Student), sizeof(Student)*(row-1));
    lseek(src, (row-1) * sizeof(Student) + sizeof(student->firstName)+2, SEEK_SET);
    sprintf(newlast, "%s>%d\n", argv[2], row);
    write(src, newlast, 20);
    close(src);
    free(student);
    return 0;
}