#include <signal.h>
#include <stdio.h>
#include <unistd.h>

void processSignal(int s){
    printf("RECIBI SENAL%d\n",s);
}

void metodoNic(int s){
     printf("NO ME PUEDES MATAR %d\n",s);
}

int main(){
    signal(10,processSignal);
    signal(15,metodoNic);
    while(1){
        sleep(3);
        printf("TRABAJANDO\n");
    }
return 1;
}