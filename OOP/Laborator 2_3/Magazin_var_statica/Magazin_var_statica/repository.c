#include "repository.h"
#include <assert.h>
#include <stdio.h>
Repo makeRepo()
{
	Repo r;
	r.v = creareVector();
	return r;
}

int find(Repo* memory, int id)
{
	/*
	Se cauta un produs in vector. Se returneaza pozitia sa sau, in caz contrar, -1.
	memory - ListaProduse
	id - string
	*/
	int i; 
	Produs p;
	for (i = 0; i < getDim(&((*memory).v)); i++)
	{
		p = getProdus(&((*memory).v), i);
		if (getId(&p)== id)
			return i;
	}
	return -1;
}

int store(Repo* memory, Produs* prod)
{
	return append(&(*memory).v,prod);
}

int updatePret(Repo* memory, Produs* prod, float pret)
{
	int poz = find(memory, getId(prod));
	if (poz != -1)
	{
		setPret(&(*memory).v.prods[poz], pret);
		return 1;
	}
	return 0;

}

int updateCantitate(Repo* memory, Produs* prod, int cantitate)
{
	int poz = find(memory, getId(prod));
	if (poz != -1)
	{
		setCantitate(&(*memory).v.prods[poz], cantitate);
		return 1;
	}
	return 0;
}

int del(Repo* memory, Produs* prod)
{
	return delete(&(*memory).v, prod);
}

Vector getAll(Repo* memory)
{
	return (*memory).v;
}

void test_store()
{
	Repo r = makeRepo();
	Produs prod = creazaProdus("101", "Casti", "Extra Bass", "Sony", "20", "50");
	assert(store(&r, &prod) == 1);
	for (int i = 1; i < getCapacitate(&r.v); i++)
		assert(store(&r, &prod) == 1);
	assert(store(&r, &prod) == 0);
}

void test_del()
{
	Repo r = makeRepo();
	Produs prod = creazaProdus("101", "Casti", "Extra Bass", "Sony", "20", "50");
	store(&r, &prod);
	assert(del(&r, &prod) == 1);
	assert(del(&r, &prod) == 0);
}

void test_find()
{
	Repo r = makeRepo();
	Produs prod1;
	prod1 = creazaProdus("101", "Casti", "Extra Bass", "Sony", "20", "50");
	assert(store(&r, &prod1)==1);
	assert(find(&r, 101) == 0);
	assert(find(&r, 102) == -1);
}

void test_update()
{
	/*Test pentru functia unpdatePret si updateCantitate*/
	Repo r = makeRepo();
	Produs prod1, prod2;
	prod1 = creazaProdus("101", "Casti", "Bass", "Sony", "20", "50");
	prod2 = creazaProdus("102", "Mouse", "Alfa", "Hama", "35", "20");
	store(&r, &prod1);
	assert(updatePret(&r, &prod1, 15) == 1);
	assert(updateCantitate(&r, &prod1, 10) == 1);
	assert(updatePret(&r, &prod2, 15) == 0);
	assert(updateCantitate(&r, &prod2, 10) == 0);
}

void test_getAll()
{
	Repo r = makeRepo();
	Vector lista = creareVector();
	Produs prod1, prod2, prod3;
	prod1 = creazaProdus("101", "Casti", "Extra Bass", "Sony", "20", "50");
	prod2 = creazaProdus("102", "Mouse", "Alfa", "Hama", "35", "20");
	prod3 = creazaProdus("103", "Tastatura", "Luna", "HP", "80", "10");
	store(&r, &prod1); 
	store(&r, &prod2);
	store(&r, &prod3);
	lista = getAll(&r);
	assert(getDim(&lista) == 3);
}