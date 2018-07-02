#include "produs.h"
#include <assert.h>
#include <string.h>
#include <stdlib.h>


Produs* creazaProdus(int id, char* tip, char* model, char* producator, double pret, int cantitate)
{
	/*
	Se creaza un nou produs.
	Se returneaza tipul Produs.
	pre: id, tip, model, producator, pret, cantitate - pointer la sir de caractere
	post: id, cantitate - nr intregi,
	tip, model, porducator - string,
	pret - nr real
	*/
	Produs* prod = (Produs*)malloc(sizeof(Produs));
	prod->id = id;
	prod->tip = malloc(sizeof(char)*(strlen(tip) + 1));
	prod->model = malloc(sizeof(char)*(strlen(model) + 1));
	prod->producator = malloc(sizeof(char)*(strlen(producator) + 1));
	prod->pret = pret;
	prod->cantitate = cantitate;

	strcpy_s(prod->tip, strlen(tip) + 1,tip);
	strcpy_s(prod->model, strlen(model) + 1,model);
	strcpy_s(prod->producator, strlen(producator) + 1, producator);
	return prod;
}

void distrugeProdus(Produs * prod)
{
	/*Se distruge un produs.
	- Se distrug pointerii la char
	*/
	free(prod->tip);
	free(prod->model);
	free(prod->producator);
	free(prod);
}

int getId(Produs* p)
{
	/*Getter pentru id produs*/
	return p->id;
}

char* getTip(Produs* p)
{
	/*Getter pentru tip produs*/
	return p->tip;
}

char* getModel(Produs* p)
{
	/*Getter pentru model produs*/
	return p->model;
}

char* getProducator(Produs* p)
{
	/*Getter pentru producator*/
	return p->producator;
}

double getPret(Produs* p)
{
	/*Getter pentru pret produs*/
	return p->pret;
}

int getCantitate(Produs* p)
{
	/*Getter pentru cantitate produs*/
	return p->cantitate;
}


int setId(Produs* p, int id)
{
	/*Setter pentru id produs*/
	p->id = id;
	return p->id;
}

char* setTip(Produs* p, char* tip)
{
	/*Setter pentru tip produs*/
	free(p->tip);
	p->tip = malloc(sizeof(char)*(strlen(tip) + 1));
	strcpy_s(p->tip, strlen(tip) + 1, tip);
	return p->tip;
}

char* setModel(Produs* p, char* model)
{
	/*Setter pentru model produs*/
	free(p->model);
	p->model = malloc(sizeof(char)*(strlen(model) + 1));
	strcpy_s(p->model, strlen(model) + 1, model);
	return p->model;
}

char* setProducator(Produs* p, char* producator)
{
	/*Setter pentru producator*/
	free(p->producator);
	p->producator = malloc(sizeof(char)*(strlen(producator) + 1));
	strcpy_s(p->producator, strlen(producator) + 1, producator);
	return p->producator;
}

double setPret(Produs* p, double pret)
{
	/*Setter pentru pret produs*/
	p->pret = pret;
	return p->pret;
}

int setCantitate(Produs* p, int cantitate)
{
	/*Setter pentru cantitate produs*/
	p->cantitate = cantitate;
	return p->cantitate;
}


int comparaProduse(Produs* p1, Produs* p2)
{
	/*
	Compara doua produse. Compararea se face dupa id-urile produselor.
	p1,p2 - pointer la Produs
	Se returneaza: 0 (produse identice), !=0 (produse distincte)
	*/
	return getId(p1) - getId(p2);
}

Produs* copieProdus(Produs p)
{
	Produs* prod = (Produs*)malloc(sizeof(Produs));
	prod->id = p.id;
	prod->tip = malloc(sizeof(char)*(strlen(p.tip) + 1));
	prod->model = malloc(sizeof(char)*(strlen(p.model) + 1));
	prod->producator = malloc(sizeof(char)*(strlen(p.producator) + 1));
	prod->pret = p.pret;
	prod->cantitate = p.cantitate;

	strcpy_s(prod->tip, strlen(p.tip) + 1, p.tip);
	strcpy_s(prod->model, strlen(p.model) + 1, p.model);
	strcpy_s(prod->producator, strlen(p.producator) + 1, p.producator);
	return prod;
}

void atribuire(Produs* p1, Produs* p2)
{
	/*
	Se copiaza p2 in variabila p1.
	p1, p2 - pointer la Produs
	*/
	setCantitate(p1, getCantitate(p2));
	setId(p1, getId(p2));
	setModel(p1, getModel(p2));
	setPret(p1, getPret(p2));
	setProducator(p1, getProducator(p2));
	setTip(p1, getTip(p2));
}

void swap(Produs* p1, Produs* p2)
{
	/*
	Se interschimba doua variabile de tip Produs,
	p1, p2 - pointer la Produs
	*/
	Produs * aux = creazaProdus(0, "fara", "fara", "fara", 1, 0);

	atribuire(aux, p1);

	atribuire(p1, p2);

	atribuire(p2, aux);

	distrugeProdus(aux);
}





///////////////////////////////////////////////////////////////////////////////////////////////
void test_creazaProdus()
{
	/*Test pentru functia creazaProdus*/
	Produs* prod = creazaProdus(101, "Casti", "Extra Bass", "Sony", 20, 50);
	assert(getId(prod) == 101);
	assert(strcmp(getTip(prod), "Casti") == 0);
	assert(strcmp(getModel(prod), "Extra Bass") == 0);
	assert(strcmp(getProducator(prod), "Sony") == 0);
	assert(getPret(prod) == 20);
	assert(getCantitate(prod) == 50);
	distrugeProdus(prod);
}

void test_copieProdus()
{
	Produs* prod = creazaProdus(101, "Casti", "Extra Bass", "Sony", 20, 50);
	Produs* copie = copieProdus(*prod);
	assert(getId(copie) == 101);
	assert(strcmp(getTip(copie), "Casti") == 0);
	assert(strcmp(getModel(copie), "Extra Bass") == 0);
	assert(strcmp(getProducator(copie), "Sony") == 0);
	assert(getPret(copie) == 20);
	assert(getCantitate(copie) == 50);
	distrugeProdus(prod);
	distrugeProdus(copie);
}

void test_comparaProduse()
{
	/*Test pentru functia comparaProduse*/
	Produs* prod1, *prod2, *prod3;
	prod1 = creazaProdus(101, "Casti", "Extra Bass", "Sony", 20, 50);
	prod2 = creazaProdus(101, "Casti", "Extra Bass", "Sony", 10, 30);
	prod3 = creazaProdus(102, "Casti", "Extra Bass", "Samsung", 20, 50);
	assert(comparaProduse(prod1, prod2) == 0);
	assert(comparaProduse(prod1, prod3) != 0);
	distrugeProdus(prod1);
	distrugeProdus(prod2);
	distrugeProdus(prod3);
}

void test_swap()
{
	/*Test pentru functia swap*/
	Produs *prod1, *prod2;
	prod1 = creazaProdus(101, "Casti", "Bass", "Sony", 20, 50);
	prod2 = creazaProdus(102, "Mouse", "001", "Hp", 55, 30);
	assert(getId(prod1) == 101);
	assert(getId(prod2) == 102);

	swap(prod1, prod2);

	assert(getId(prod1) == 102);
	assert(getId(prod2) == 101);

	assert(strcmp(getTip(prod1), "Mouse") == 0);
	assert(strcmp(getTip(prod2), "Casti") == 0);

	assert(strcmp(getModel(prod1), "001") == 0);
	assert(strcmp(getModel(prod2), "Bass") == 0);

	assert(strcmp(getProducator(prod1), "Hp") == 0);
	assert(strcmp(getProducator(prod2), "Sony") == 0);

	assert(getCantitate(prod1) == 30);
	assert(getCantitate(prod2) == 50);

	assert(getPret(prod1) == 55);
	assert(getPret(prod2) == 20);
	distrugeProdus(prod1);
	distrugeProdus(prod2);

}

void test_atribuire()
{
	/*Test pentru functia atribuire*/
	Produs *p1, *p2;
	p2 = creazaProdus(101, "Casti", "Bass", "Sony", 20, 50);
	p1 = creazaProdus(0, "fara", "fara", "fara", 1, 0);
	atribuire(p1, p2);
	assert(getId(p1) == 101);
	assert(getPret(p1) == 20);
	assert(getCantitate(p1) == 50);
	assert(strcmp(getTip(p1), "Casti") == 0);
	assert(strcmp(getModel(p1), "Bass") == 0);
	assert(strcmp(getProducator(p1), "Sony") == 0);
	distrugeProdus(p1);
	distrugeProdus(p2);
}

void test_set()
{
	/*Test pentru settere*/
	Produs *p = creazaProdus(101, "Casti", "Bass", "Sony", 20, 50);
	assert(setId(p, 1) == 1);
	assert(strcmp(setTip(p, "Mouse"), "Mouse") == 0);
	assert(strcmp(setModel(p, "Alf"), "Alf") == 0);
	assert(strcmp(setProducator(p, "HP"), "HP") == 0);
	assert(setPret(p, 80) == 80);
	assert(setCantitate(p, 10) == 10);
	distrugeProdus(p);
}