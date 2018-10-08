#pragma once
#ifndef LISTA_H
#define LISTA_H


//tip de data generic (pentru moment este intreg)
typedef int TElem;

//referire a structurii Nod;
struct Nod;

//se defineste tipul PNod ca fiind adresa unui Nod
typedef Nod *PNod;

typedef struct Nod {
	TElem e;
	PNod urm;
};

typedef struct {
	//prim este adresa primului Nod din lista
	PNod _prim;
} Lista;

//operatii pe lista - INTERFATA

//crearea unei liste din valori citite pana la 0
Lista creare();
//tiparirea elementelor unei liste
void tipar(Lista l);
// destructorul listei
void distruge(Lista l);

/*
 1. 
 a. Sa se substituie toate aparitiile unui element dintr-o lista cu o alta
 lista.
 b. Sa se determine elementul de pe pozitia a n-a unei liste.
*/

PNod substituie(Lista l1, int e, Lista l2);
int element(Lista l, int k);

/*
 5. 
 a. Sa se verifice dacă o lista este multime.
 b. Sa se determine numarul elementelor distincte dintr-o lista.
*/

bool eMultime(Lista l);
int nrDistincte(Lista l);


/*
 4. 
 a. Să se inverseze o listă.
*/

Lista invers(Lista l);

#endif