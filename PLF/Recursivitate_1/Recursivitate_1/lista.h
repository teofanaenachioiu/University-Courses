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

/* 3. 
a. Check if a list is a set.
b. Determine the number of distinct elements from a list.
*/
bool isSet(Lista l);
int nrDist(Lista l);


/* 4. 
a. Determine if a list has even number of elements, without computing the length of the list.
b. Delete all occurrences of an element e from a list.
*/
bool evenNrElems(Lista l);
Lista deleteElem(TElem e, Lista l);

/* 5. 
a. Determine the greatest common divisors of elements from a list.
b. Insert an element on the n-position in a list.
*/
int cmmdc(Lista l);
void insertElem(int e, int poz, Lista l);

/* 6. 
a.Add an element at the end of a list.
b.Concatenate two lists.
*/
void adaugaSfarsit(int el, Lista l);
Lista concatenare(Lista l1, Lista l2);

/* 7. 
a. Test the equality of two lists.
b. Determine the intersection of two sets represented as lists.
*/
bool egale(Lista l1, Lista l2);
Lista intersectie(Lista l1, Lista l2);

/* 8. 
a. Determine the lowest common multiple of the elements from a list.
b. Substitute in a list, all occurrence of a value e with a value e1.
*/
int cmmmc(Lista l);
Lista substituire(Lista l, int e, int e1);

/* 9. 
a. Invert a list
b. Determine the maximum element of a numerical list.
*/

Lista inversare(Lista l);
int elemMax(Lista l);

/* 10. 
a. Determine the number formed by adding all even elements and subtracting all odd numbers of the list.
b. Determine difference of two sets represented as lists.
*/

int detNumar(Lista l);
Lista diferenta(Lista l1, Lista l2);

/* 11. 
a. Determine if a certain element is member in a list.
b. Determine the length of a list.
*/
PNod cauta(TElem e, Lista l);
int dimensiune(Lista l);


/* 12. 
a. Test the inclusion of two lists
b. Insert in a list, after value e, a new value e1.
*/

/* 13. 
a. Test the inclusion of two sets, represented as lists.
b. Eliminate all occurrences of an element from a list.
*/

/* 14. 
a. Determine the last element of a list.
b. Delete elements from a list, from position n to n.
*/

/* 15. 
a. Substitute all occurrences of an element from a list with another list.
b. Determine the element from the n-th position in a list.
*/
#endif
