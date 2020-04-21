#include<sys/ipc.h>
#include<sys/shm.h>
#include<stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

int main(){
    //400 POR EL TAMANIO DE LOS ENTEROS 1 INT = 4
    int shmId=shmget(29,400,0644|IPC_CREAT);  //este lo ejecuta el proceso padre, es el que crea el proceso de memoria
    int hijos[5];
    for(int i=0;i<5;i++){
        int pid = fork(); //para crear un nuevo proceso
        hijos[i]=pid;
        if(pid!=0){ //si soy el proceso hijo entra 
        int shmId=shmget(29,400,0644);
        int *var=(int *)shmat(shmId,NULL,0);
        var=&var[i*10]; //aqui muevo el apuntador para q  no se sobreescriba 
        for(int j=0;j<10;j++){
            var[j]=i;
        }
        return 0; //el hijo se sale y el padre es el que continua ejecutando el for 
        }
    }
    for(int i=0;i<5;i++){ //para asegurarte que los procesos hijos hallan termiando
        waitpid(hijos[i],0,0);
    }
    int *var=(int *)shmat(shmId,NULL,0);
    for(int x=0;x<50;x++){
         printf("%d",var[x]);
    }

    return 0;
}