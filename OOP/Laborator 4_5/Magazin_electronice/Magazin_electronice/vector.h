#pragma once
#include "produs.h"

typedef struct {
	Produs** prods;
	int dim, capacitate;
}Vector;

Vector* creareVector();

void distrugeVector(Vector * v);

void resize(Vector *v);

Produs* getProdus(Vector* v, int poz);

int getDim(Vector* v);

int incremDim(Vector* v);

int getCapacitate(Vector* v);

void swapElem(Vector* v, int i, int j);

int search(Vector* v, Produs* prod);

int append(Vector* v, Produs* prod);

int delete(Vector* v, Produs* prod);


void test_creazaVector();

void test_getProdus();

void test_search();

void test_append();

void test_delete();

void test_swapElem();
