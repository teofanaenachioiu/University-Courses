#pragma once
#include "repository.h"

typedef struct
{
	Repo r;
}Service;

Service creareService();

int adaugare(Service* serv, Produs* prod);

int actualizarePret(Service* serv, Produs* prod, float pret);

int actualizareCantitate(Service* serv, Produs* prod, int cantitate);

int stergere(Service* serv, Produs* prod);

int cauta(Service* serv, Produs* prod);

Vector iaTot(Service * serv);

Vector sortarePret(Vector *lista, Service* serv, int reverse);

Vector sortareCantitate(Vector* lista, Service* serv, int reverse);

Vector listaSortata(Service* serv, int mod);

Vector getProducatorList(Service * serv, char producator[20]);

Vector getPretList(Service * serv, float pret);

Vector getCantitateList(Service * serv, int cantitate);


void test_adaugaProdus();

void test_stergeProdus();

void test_cautaProdus();

void test_actualizarePret();

void test_actualizareCantitate();

void test_iaTot();

void test_getProducatorList();

void test_getPretList();

void test_getCantitateList();

void test_listaSortata();

void test_sortarePret();

void test_sortareCantitate();