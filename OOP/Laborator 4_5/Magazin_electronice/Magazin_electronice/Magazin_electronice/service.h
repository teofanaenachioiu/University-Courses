#pragma once
#include "repository.h"

typedef struct
{
	Repo* r;
	Vector*undo;
}Service;

Service* creareService();

void distrugeService(Service * serv);

int adaugare(Service* serv, Produs* prod);

int actualizarePret(Service* serv, Produs* prod, double pret);

int actualizareCantitate(Service* serv, Produs* prod, int cantitate);

int stergere(Service* serv, Produs* prod);

int cauta(Service* serv, Produs* prod);

Vector* iaTot(Service * serv);

Vector* sortarePret(Service* serv, int reverse);

Vector* sortareCantitate(Service* serv, int reverse);

Vector* listaSortata(Service* serv, char mod);

void getProducatorList(Vector* list, Service * serv, char* producator);

void getModelList(Vector * list, Service * serv, char * model);

void getPretList(Vector* list, Service * serv, double pret);

int makeUndo(Service * serv);

void getCantitateList(Vector* list, Service * serv, int cantitate);


void test_adaugaProdus();

void test_stergeProdus();

void test_cautaProdus();

void test_actualizarePret();

void test_actualizareCantitate();

void test_iaTot();

void test_getProducatorList();

void test_getPretList();

void test_getCantitateList();

void test_sortarePret();

void test_sortareCantitate();