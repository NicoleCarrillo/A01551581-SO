#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>

typedef struct s{
    unsigned char id;
    unsigned char semester;
    char firstName[20];
    char lastName[20];
} Student;

int main(int argn, char **argv){
    int row = atoi(argv[1]);

    Student *student;
    student = (Student *)malloc(sizeof(Student));

    int src = open("class.bin", O_RDWR);
    pread(src, student, sizeof(Student), sizeof(Student)*(row-1));
    printf("Estudiante: %d, %d, %s, %s",
    student->id,
    student->semester,
    student->firstName,
    student->lastName);
    close(src);
    free(student);
    return 0;
}