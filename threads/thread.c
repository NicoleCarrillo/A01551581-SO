#include<pthread.h>
#include<stdio.h>
#define NUMTHREADS 5
//DEFINE TE AYUDA A CREAR CONSTANTES

int val =5;
pthread_mutex_t mutex;

//ESTE METODO EL * ES DE TIPO GENERICO 
void * hello(void *id){ //ESTA ES LA QUE EJECUTARIAN LOS HILOS
    //ESTA ES LA REGION CRITICA
    pthread_mutex_lock(&mutex);
    printf("HELLO WORLD %ld,%d\n",(long)id,val);
    val +=val;
    pthread_mutex_unlock(&mutex);
};

int main(){// PROCESO PRINCIPAL QUE VA A CREAR A LOS HILOS
   pthread_t hilos[NUMTHREADS];
   for(long i=0;i<NUMTHREADS;i++){
       pthread_create(&hilos[i],NULL,hello,(void *)i);
   }
    pthread_exit(NULL);
}