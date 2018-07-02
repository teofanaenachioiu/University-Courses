#include "vector.h"
#include <assert.h>

Vector creareVector()
{
	/*
	Se creaza o noua lista in care vor fi memorate produsele.
	Se returneaza tipul Vector
	*/
	Vector v;
	v.dim = 0;
	v.capacitate = 100;
	return v;
}

int getDim(Vector* v)
{
	return (*v).dim;
}

int incremDim(Vector* v)
{
	(*v).dim = (*v).dim + 1;
	return (*v).dim;
}

int getCapacitate(Vector* v)
{
	return (*v).capacitate;
}

Produs getProdus(Vector* v, int poz)
{
	return (*v).prods[poz];
}

void swapElem(Vector* v, int i, int j)
{
	swap(&(*v).prods[i], &(*v).prods[j]);
}

int search(Vector* v, Produs* prod)
{
	int i;
	Produs p;
	for (i = 0; i < getDim(v); i++)
	{
		p = getProdus(v, i);
		if (comparaProduse(&p, prod) == 0)
			return i;
	}
	return -1;
}

int append(Vector* v, Produs* prod)
{
	if (getDim(v) < getCapacitate(v))
	{
		(*v).prods[(*v).dim] = *prod;
		incremDim(v);
		return 1;}
	else return 0;
}

int delete(Vector* v, Produs* prod)
{
	int i, poz = search(v, prod);
	if (poz!=-1)
	{
		for (i = poz; i < getDim(v) - 1; i++)
			(*v).prods[i] = (*v).prods[i+1];
		(*v).dim = (*v).dim - 1;
		return 1;}
	else return 0;
}

void test_creazaVector()
{
	/*Test pentru functia creazaLista*/
	Vector v = creareVector();
	assert(getDim(&v)== 0);
	assert(getCapacitate(&v) == 100);
	assert(incremDim(&v) == 1);
}

void test_getProdus()
{
	Vector v = creareVector();
	Produs prod1, prod2;
	prod1 = creazaProdus("101", "Casti", "Extra Bass", "Sony", "20", "50");
	append(&v, &prod1);
	prod2 = getProdus(&v, 0);
	assert(comparaProduse(&prod1,&prod2)==0);
}

void test_search()
{
	Vector v = creareVector();
	Produs prod1, prod2;
	prod1 = creazaProdus("101", "Casti", "Extra Bass", "Sony", "20", "50");
	prod2 = creazaProdus("102", "Mouse", "Alfa", "Hama", "35", "20");
	append(&v, &prod1);
	assert(search(&v,&prod1)==0);
	assert(search(&v, &prod2) == -1);
}

void test_append()
{
	Vector v = creareVector();
	Produs prod = creazaProdus("101", "Casti", "Extra Bass", "Sony", "20", "50");
	assert(append(&v, &prod)==1);
	for (int i = 1; i < getCapacitate(&v); i++)
		append(&v, &prod);
	assert(append(&v, &prod) == 0);
}

void test_delete()
{
	Vector v = creareVector();
	Produs prod1, prod2,prod3;
	prod1 = creazaProdus("101", "Casti", "Extra Bass", "Sony", "20", "50");
	prod2 = creazaProdus("102", "Mouse", "Alfa", "Hama", "35", "20");
	prod3 = creazaProdus("103", "Tastatura", "Luna", "HP", "80", "10");
	append(&v, &prod1);
	append(&v, &prod2);
	assert(delete(&v, &prod1) == 1);
	assert(delete(&v, &prod3) == 0);
}

void test_swapElem()
{
	Vector v = creareVector();
	Produs prod1, prod2, prod3;
	prod1 = creazaProdus("101", "Casti", "Extra Bass", "Sony", "20", "50");
	prod2 = creazaProdus("102", "Mouse", "Alfa", "Hama", "35", "20");
	prod3 = creazaProdus("103", "Tastatura", "Luna", "HP", "80", "10");
	append(&v, &prod1);
	append(&v, &prod2);
	append(&v, &prod3);
	swapElem(&v, 0, 2);
	prod1 = getProdus(&v, 0);
	prod2 = getProdus(&v, 1);
	prod3 = getProdus(&v, 2);
	assert(getId(&prod1)==103);
	assert(getId(&prod2) == 102);
	assert(getId(&prod3) == 101);
}