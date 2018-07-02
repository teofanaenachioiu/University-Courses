#include "repository.h"
#include <assert.h>
#include <stdio.h>
#include <malloc.h>

Repo* makeRepo()
{
	/*Se creaza un Repository.*/
	Repo* r = (Repo*)malloc(sizeof(Repo));
	r->v = creareVector();
	return r;
}
 
void destroyRepo(Repo * memory)
{
	/*Functia de distrugere Repository.*/
	distrugeVectorProduse(memory->v);
	free(memory);
}

int find(Repo* memory, int id)
{
	/*
	Se cauta un produs in Repository. Se returneaza pozitia sa sau, in caz contrar, -1.
	memory - Repo
	id - nr intreg
	*/
	int i;
	for (i = 0; i < getDim(getAll(memory)); i++)
	{
		if (getId(get(getAll(memory), i)) == id)
		{
			return i;
		}
	}
	return -1;
}

int store(Repo* memory, Produs* prod)
{
	/*Se adauga un produs in Repository.*/
	return append(memory->v, prod);
}

int updatePret(Repo* memory, Produs* prod, double pret)
{
	/*Se actualizeaza pretul unui produs.
	prod - Produs - produsul de actualizat
	pret - nr real - noul pret al produsului
	*/
	int poz = find(memory, getId(prod));
	if (poz != -1)
	{
		setPret(get(getAll(memory), poz), pret);
		return 1;
	}
	return 0;

}

int updateCantitate(Repo* memory, Produs* prod, int cantitate)
{
	/*Se actualizeaza cantiatea produsului.
	prod - Produs - produsul de actualizat
	cantitate - nr intreg - noul stoc al produsului
	*/
	int poz = find(memory, getId(prod));
	if (poz != -1)
	{
		setCantitate(get(getAll(memory), poz), cantitate);
		return 1;
	}
	return 0;
}

int del(Repo* memory, Produs* prod)
{
	/*Sterge un produs din repository
	prod - produsul de sters
	*/
	return delete(memory->v, prod);
}

Vector* getAll(Repo* memory)
{
	/*Functia ia toate produsele din repository.*/
	return memory->v;
}

void test_store()
{
	/*Test pentru functia store*/
	Repo *r = makeRepo();
	Produs *prod = creazaProdus(101, "Casti", "Extra Bass", "Sony", 20, 50);
	assert(store(r, prod) == 1);
	destroyRepo(r);
}

void test_del()
{
	/*Test pentru functia del*/
	Repo* r = makeRepo();
	Produs* prod = creazaProdus(101, "Casti", "Extra Bass", "Sony", 20, 50);
	assert(store(r, prod) == 1);
	assert(del(r, prod) == 1);
	Produs* prod1 = creazaProdus(102, "Mouse", "Extra", "Hp", 20, 50);
	assert(del(r, prod1) == 0);
	distrugeProdus(prod1);
	destroyRepo(r);
}

void test_find()
{
	/*Test pentru functia find*/
	Repo *r = makeRepo();
	Produs *prod1 = creazaProdus(101, "Casti", "Extra Bass", "Sony", 20, 50);
	assert(store(r, prod1) == 1);
	assert(find(r, 101) == 0);
	assert(find(r, 102) == -1);
	destroyRepo(r);
}

void test_update()
{
	/*Test pentru functia unpdatePret si updateCantitate*/
	Repo *r = makeRepo();
	Produs *prod1, *prod2;
	prod1 = creazaProdus(101, "Casti", "Bass", "Sony", 20, 50);
	prod2 = creazaProdus(102, "Mouse", "Alfa", "Hama", 35, 20);
	store(r, prod1);
	assert(updatePret(r, prod1, 15) == 1);
	assert(updateCantitate(r, prod1, 10) == 1);
	assert(updatePret(r, prod2, 15) == 0);
	assert(updateCantitate(r, prod2, 10) == 0);
	distrugeProdus(prod2);
	destroyRepo(r);

}

void test_getAll()
{
	/*Test pentru functia getAll*/
	Repo *r = makeRepo();
	Produs *prod1, *prod2, *prod3;
	prod1 = creazaProdus(101, "Casti", "Extra Bass", "Sony", 20, 50);
	prod2 = creazaProdus(102, "Mouse", "Alfa", "Hama", 35, 20);
	prod3 = creazaProdus(103, "Tastatura", "Luna", "HP", 80, 10);
	store(r, prod1);
	store(r, prod2);
	store(r, prod3);
	assert(getDim(getAll(r)) == 3);
	destroyRepo(r);
}