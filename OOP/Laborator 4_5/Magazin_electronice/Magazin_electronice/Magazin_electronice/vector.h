#pragma once
#include "produs.h"

typedef void* TElement;


typedef struct {
	TElement* list;
	int dim;
	int capacitate;

}Vector;

Vector* creareVector();

Vector * copyList(Vector * l);

void distrugeVectorProduse(Vector * v);

TElement removeLast(Vector * l);

void resize(Vector *v);

TElement get(Vector* v, int poz);

int getDim(Vector* v);

int incremDim(Vector* v);

int getCapacitate(Vector* v);

void swapElem(Vector* v, int i, int j);

int searchProdus(Vector* v, TElement prod);

int append(Vector* v, TElement  prod);

int delete(Vector* v, TElement prod);


void test_creazaVector();

void test_getProdus();

void test_search();

void test_append();

void test_delete();

void test_swapElem();
