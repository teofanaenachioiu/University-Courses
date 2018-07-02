#include "vector.h"
#include "produs.h"
#include <assert.h>
#include <malloc.h>
#include <stdio.h>
Vector* creareVector()
{
	/*
	Se creaza un vector in care vor fi memorate produsele.
	return: pointer la Vector
	post: dim vector=0
	capacitate vector=100
	*/
	Vector* v = (Vector*)malloc(sizeof(Vector));
	v->dim = 0;
	v->capacitate = 100;
	v->prods = (Produs**)malloc(sizeof(Produs*)*(v->capacitate));
	return v;
}



void distrugeVector(Vector * v)
{
	/*Functia de distrugere vector.
	Se distruge fiecare produs din lista*/
	for (int k = 0; k < getDim(v); k++)
		distrugeProdus(getProdus(v, k));
	free(v->prods);
	free(v);
}

int getDim(Vector* v)
{
	/*Getter dimensiune vector*/
	return v->dim;
}

int incremDim(Vector* v)
{
	/*Incrementeaza dimensiunea vectorului*/
	v->dim = v->dim + 1;
	return v->dim;
}

int getCapacitate(Vector* v)
{
	/*Getter capacitate vector*/
	return v->capacitate;
}


Produs* getProdus(Vector* v, int poz)
{
	/*Getter pentru pordusul de pe pozitia poz
	poz - nr intreg*/
	return v->prods[poz];
}

void resize(Vector *v)
{
	/*Dubleaza capacitatea unui vector*/
	Vector* v_copy = (Vector*)malloc(sizeof(Vector));
	v_copy->dim = 0;
	v_copy->capacitate = getCapacitate(v) * 2;
	v_copy->prods = (Produs**)malloc(sizeof(Produs*)*(v->capacitate));

	for (int i = 0; i < getDim(v); i++)
		append(v_copy, getProdus(v, i));

	free(v->prods);
	v->capacitate = v_copy->capacitate;
	v->dim = 0;
	v->prods = (Produs**)malloc(sizeof(Produs*)*(v->capacitate));
	for (int i = 0; i < getDim(v_copy); i++)
		append(v, getProdus(v_copy, i));
	free(v_copy->prods);
	free(v_copy);
}

void swapElem(Vector* v, int i, int j)
{
	/*Se interschimba elementele de pe pozitiile i si j din vector.*/
	swap(v->prods[i], v->prods[j]);
}

int search(Vector* v, Produs* prod)
{
	/*Cauta un produs in vector.
	Return: i (nr natural => pozitia din vector), -1(produsul nu exista in vector)*/
	int i;
	for (i = 0; i < getDim(v); i++)
	{
		if (comparaProduse(getProdus(v, i), prod) == 0)
			return i;
	}
	return -1;
}

int append(Vector* v, Produs* prod)
{
	/*Adauga un produs in vector.
	return: 1 (capacitatea vectorului nu a fost modificata), 0(capacitatea a fost dublata)*/
	if (getDim(v) < getCapacitate(v))
	{

		(v->prods)[v->dim] = prod;
		incremDim(v);
		return 1;}
	else {
		resize(v);
		(v->prods)[v->dim] = prod;
		incremDim(v);
		return 0;
	}
}

int delete(Vector* v, Produs* prod)
{
	/*Cauta un produs in vector.
	Return: 1(produsul a fost sters) sau -1(produsul nu exista in vector)*/
	int i, poz = search(v, prod);
	if (poz != -1)
	{
		for (i = poz; i < getDim(v) - 1; i++)
			v->prods[i] = v->prods[i + 1];
		v->dim = v->dim - 1;
		return 1;	}
	else return 0;
}

void test_creazaVector()
{
	/*Test pentru functia creazaLista*/
	Vector *v = creareVector();
	assert(getDim(v) == 0);
	assert(getCapacitate(v) == 100);
	distrugeVector(v);
}

void test_getProdus()
{
	/*Test pentru functia getProdus*/
	Vector* v = creareVector();
	Produs *prod1;
	prod1 = creazaProdus(101, "Casti", "ExtraBass", "Sony", 20, 50);
	append(v, prod1);
	assert(comparaProduse(prod1, getProdus(v, 0)) == 0);
	distrugeVector(v);
}

void test_search()
{
	/*Test pentru functia search*/
	Vector *v = creareVector();
	Produs *prod1, *prod2;
	prod1 = creazaProdus(101, "Casti", "Extra Bass", "Sony", 20, 50);
	prod2 = creazaProdus(102, "Mouse", "Alfa", "Hama", 35, 20);
	append(v, prod1);
	assert(search(v, prod1) == 0);
	assert(search(v, prod2) == -1);
	distrugeProdus(prod2);
	distrugeVector(v);
}

void test_append()
{
	/*Teste pentru functiile append si resize*/
	Vector* v = creareVector();
	Produs *prod = creazaProdus(101, "Casti", "Extra Bass", "Sony", 20, 50);
	assert(append(v, prod) == 1);
	assert(getDim(v) == 1);
	for (int i = 1; i < getCapacitate(v); i++)
	{
		Produs *p = creazaProdus(101, "Casti", "Extra Bass", "Sony", 20, 50);
		append(v, p);
	}
	Produs *pr = creazaProdus(101, "Casti", "Extra Bass", "Sony", 20, 50);
	assert(append(v, pr) == 0);
	Produs *pro = creazaProdus(101, "Casti", "Extra Bass", "Sony", 20, 50);
	assert(append(v, pro) == 1);
	distrugeVector(v);
}

void test_delete()
{
	/*Test pentru functia delete*/
	Vector* v = creareVector();
	Produs *prod1, *prod2, *prod3;
	prod1 = creazaProdus(101, "Casti", "Extra Bass", "Sony", 20, 50);
	prod2 = creazaProdus(102, "Mouse", "Alfa", "Hama", 35, 20);
	prod3 = creazaProdus(103, "Tastatura", "Luna", "HP", 80, 10);
	append(v, prod1);
	append(v, prod2);
	assert(delete(v, prod1) == 1);
	assert(delete(v, prod3) == 0);
	distrugeProdus(prod1);
	distrugeProdus(prod3);
	distrugeVector(v);
}

void test_swapElem()
{
	/*Test pentru functia swapElem*/
	Vector *v = creareVector();
	Produs *prod1, *prod2, *prod3;
	prod1 = creazaProdus(101, "Casti", "Extra Bass", "Sony", 20, 50);
	prod2 = creazaProdus(102, "Mouse", "Alfa", "Hama", 35, 20);
	prod3 = creazaProdus(103, "Tastatura", "Luna", "HP", 80, 10);
	append(v, prod1);
	append(v, prod2);
	append(v, prod3);
	swapElem(v, 0, 2);
	prod1 = getProdus(v, 0);
	prod2 = getProdus(v, 1);
	prod3 = getProdus(v, 2);
	assert(getId(prod1) == 103);
	assert(getId(prod2) == 102);
	assert(getId(prod3) == 101);
	distrugeVector(v);
}