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

bool eListaVida(Lista l);

Lista creeazaListaVida();

int primElement(Lista l);

Lista sublista(Lista l); 

Lista adaugaInceput(int elem, Lista l);

/*
 6. 
 a. Sa se determine daca lista are numar par de elemente, fara sa se
 calculeze lungimea.
 b. Sa se stearga toate aparitiile unui element e dintr-o lista.
*/

bool nrParElem(Lista l);

Lista stergeElem(Lista l, int elem);

#endif
