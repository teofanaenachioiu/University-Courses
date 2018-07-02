#include "produs.h"
#include <assert.h>
#include <string.h>
#include <stdlib.h>
#include <stdio.h>

Produs creazaProdus(char id[20], char tip[20], char model[20], char producator[20], char pret[20], char cantitate[20])
{
	/*
	Se creaza un nou produs.
	Se returneaza tipul Produs.
	*/
	Produs prod;
	prod.id=atoi(id);
	strcpy(prod.tip, tip);
	strcpy(prod.model, model);
	strcpy(prod.producator, producator);
	prod.pret = atof (pret);
	prod.cantitate = atoi(cantitate);
	return prod;
}

int getId(Produs* p)
{
	/*Getter pentru id produs*/
	return (*p).id;
}

char* getTip(Produs* p)
{
	/*Getter pentru tip produs*/
	return (*p).tip;
}

char* getModel(Produs* p)
{
	/*Getter pentru model produs*/
	return (*p).model;
}

char* getProducator(Produs* p)
{
	/*Getter pentru producator*/
	return (*p).producator;
}

float getPret(Produs* p)
{
	/*Getter pentru pret produs*/
	return (*p).pret;
}

int getCantitate(Produs* p)
{
	/*Getter pentru cantitate produs*/
	return (*p).cantitate;
}


int setId(Produs* p, int id)
{
	/*Getter pentru id produs*/
	(*p).id = id;
	return (*p).id;
}

char* setTip(Produs* p, char tip[20])
{
	/*Getter pentru tip produs*/
	strcpy((*p).tip, tip);
	return (*p).tip;
}

char* setModel(Produs* p, char model[20])
{
	/*Getter pentru model produs*/
	strcpy((*p).model, model);
	return (*p).model;
}

char* setProducator(Produs* p, char producator[20])
{
	/*Getter pentru producator*/
	strcpy((*p).producator, producator);
	return (*p).producator;
}

float setPret(Produs* p, float pret)
{
	/*Getter pentru pret produs*/
	(*p).pret = pret;
	return (*p).pret;
}

int setCantitate(Produs* p, int cantitate)
{
	/*Getter pentru cantitate produs*/
	(*p).cantitate = cantitate;
	return (*p).cantitate;
}


int comparaProduse(Produs* p1, Produs* p2)
{
	/*
	Compara doua produse. Compararea se face dupa id-urile produselor.
	p1,p2 - Produs
	return 1 (produse distincte), 0 (produse identice)
	*/
	return getId(p1)- getId(p2);
}

void swap(Produs* p1, Produs* p2)
{
	/*
	Se interschimba doua variabile de tip Produs,
	p1, p2 - Produs
	*/
	Produs aux = creazaProdus("0", "fara", "fara", "fara", "1", "0");


	aux.id=getId(p1);
	strcpy(aux.model, getModel(p1));
	strcpy(aux.tip, getTip(p1));
	aux.cantitate= getCantitate(p1);
	strcpy(aux.producator, getProducator(p1));
	aux.pret=getPret(p1);

	
	assert(getPret(&aux) == getPret(p1));

	(*p1).id = getId(p2);
	strcpy((*p1).model, getModel(p2));
	strcpy((*p1).tip, getTip(p2));
	(*p1).cantitate = getCantitate(p2);
	strcpy((*p1).producator, getProducator(p2));
	(*p1).pret = getPret(p2);

	(*p2).id = getId(&aux);
	strcpy((*p2).model, getModel(&aux));
	strcpy((*p2).tip, getTip(&aux));
	(*p2).cantitate = getCantitate(&aux);
	strcpy((*p2).producator, getProducator(&aux));
	(*p2).pret = getPret(&aux);
}

void atribuire(Produs* p1, Produs* p2)
{
	/*
	Se copiaza p2 in variabila p1.
	p1, p2 - Produs
	*/
	(*p1).cantitate = getCantitate(p2);
	(*p1).id = getId(p2);
	strcpy((*p1).model, getModel(p2));
	(*p1).pret = getPret(p2);
	strcpy((*p1).producator, getProducator(p2));
	strcpy((*p1).tip, getTip(p2));
}



///////////////////////////////////////////////////////////////////////////////////////////////
void test_creazaProdus()
{
	/*Test pentru functia creazaProdus*/
	Produs prod = creazaProdus("101", "Casti", "Extra Bass", "Sony", "20", "50");
	assert(getId(&prod)==101);
	assert(strcmp(getTip(&prod), "Casti") == 0);
	assert(strcmp(getModel(&prod), "Extra Bass") == 0);
	assert(strcmp(getProducator(&prod), "Sony") == 0);
	assert(getPret(&prod) == 20);
	assert(getCantitate(&prod) == 50);
}

void test_comparaProduse()
{
	Produs prod1, prod2, prod3;
	prod1 = creazaProdus("101", "Casti", "Extra Bass", "Sony", "20", "50");
	prod2 = creazaProdus("101", "Casti", "Extra Bass", "Sony", "10", "30");
	prod3 = creazaProdus("102", "Casti", "Extra Bass", "Samsung", "20", "50");
	assert(comparaProduse(&prod1, &prod2) == 0);
	assert(comparaProduse(&prod1, &prod3) != 0);
}

void test_swap()
{
	Produs prod1, prod2;
	prod1 = creazaProdus("101", "Casti", "Bass", "Sony", "20", "50");
	prod2 = creazaProdus("102", "Mouse", "001", "Hp", "55", "30");
	assert(getId(&prod1) == 101);
	assert(getId(&prod2) == 102);
	swap(&prod1, &prod2);

	assert(getId(&prod1) == 102);
	assert(getId(&prod2) == 101);

	assert(strcmp(getTip(&prod1),"Mouse") == 0);
	assert(strcmp(getTip(&prod2), "Casti") == 0);

	assert(strcmp(getModel(&prod1), "001") == 0);
	assert(strcmp(getModel(&prod2), "Bass") == 0);

	assert(strcmp(getProducator(&prod1), "Hp") == 0);
	assert(strcmp(getProducator(&prod2), "Sony") == 0);

	assert(getCantitate(&prod1) == 30);
	assert(getCantitate(&prod2) == 50);

	assert(getPret(&prod1) == 55);
	assert(getPret(&prod2) == 20);

}

void test_atribuire()
{
	/*Test pentru functia atribuire*/
	Produs p1, p2;
	p2 = creazaProdus("101", "Casti", "Bass", "Sony", "20", "50");
	atribuire(&p1, &p2);
	assert(getId(&p1) == 101);
	assert(getPret(&p1) == 20);
	assert(getCantitate(&p1) == 50);
	assert(strcmp(getTip(&p1), "Casti") == 0);
	assert(strcmp(getModel(&p1), "Bass") == 0);
	assert(strcmp(getProducator(&p1), "Sony") == 0);
}

void test_set()
{
	Produs p= creazaProdus("101", "Casti", "Bass", "Sony", "20", "50");
	assert(setId(&p, 1) == 1);
	assert(strcmp(setTip(&p, "Mouse"), "Mouse") == 0);
	assert(strcmp(setModel(&p, "Alfa"), "Alfa") == 0);
	assert(strcmp(setProducator(&p, "HP"), "HP") == 0);
	assert(setPret(&p, 80) == 80);
	assert(setCantitate(&p, 10) == 10);
}